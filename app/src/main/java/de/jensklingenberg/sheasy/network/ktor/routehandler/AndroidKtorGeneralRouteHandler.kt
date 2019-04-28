package de.jensklingenberg.sheasy.network.ktor.routehandler

import android.util.Log
import com.squareup.moshi.Moshi
import com.squareup.moshi.Types
import de.jensklingenberg.sheasy.App
import de.jensklingenberg.sheasy.data.DevicesDataSource
import de.jensklingenberg.sheasy.data.FileDataSource
import de.jensklingenberg.sheasy.data.event.EventDataSource
import de.jensklingenberg.sheasy.data.notification.NotificationDataSource
import de.jensklingenberg.sheasy.model.*
import de.jensklingenberg.sheasy.network.SheasyPrefDataSource
import de.jensklingenberg.sheasy.network.extension.ktorApplicationCall
import de.jensklingenberg.sheasy.network.routehandler.GeneralRouteHandler
import de.jensklingenberg.sheasy.data.usecase.NotificationUseCase
import de.jensklingenberg.sheasy.utils.extension.debugCorsHeader
import de.jensklingenberg.sheasy.utils.toplevel.runInBackground
import io.ktor.application.ApplicationCall
import io.ktor.application.ApplicationCallPipeline
import io.ktor.application.call
import io.ktor.http.cio.websocket.CloseReason
import io.ktor.http.cio.websocket.Frame
import io.ktor.http.cio.websocket.close
import io.ktor.http.cio.websocket.readText
import io.ktor.request.receive
import io.ktor.request.uri
import io.ktor.response.respond
import io.ktor.routing.Route
import io.ktor.routing.get
import io.ktor.websocket.webSocket

import io.reactivex.Completable
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.addTo
import io.reactivex.rxkotlin.subscribeBy
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.channels.SendChannel
import kotlinx.coroutines.channels.consumeEach
import kotlinx.coroutines.channels.mapNotNull
import kotlinx.coroutines.launch
import kotlinx.coroutines.rx2.await
import java.util.*
import javax.inject.Inject

class AndroidKtorGeneralRouteHandler : GeneralRouteHandler {



    @Inject
    lateinit var sheasyPrefDataSource: SheasyPrefDataSource

    @Inject
    lateinit var notificationUseCase: NotificationUseCase

    @Inject
    lateinit var fileDataSource: FileDataSource

    @Inject
    lateinit var eventDataSource: EventDataSource

    @Inject
    lateinit var devicesDataSource: DevicesDataSource

    @Inject
    lateinit var notificationDataSource: NotificationDataSource

    @Inject
    lateinit var moshi: Moshi




    init {
        initializeDagger()

    }


    private fun initializeDagger() = App.appComponent.inject(this)

    private fun intercept(call: ApplicationCall): Completable = Completable.create { completableEmitter ->
        val filepath = call.request.uri

        val sheasyCall = call.ktorApplicationCall(filepath)

        if (sheasyPrefDataSource.acceptAllConnections) {
            if (devicesDataSource.auth.none { device -> device.ip == sheasyCall.remoteHostIp }) {
                devicesDataSource.addAuthorizedDevice(
                    Device(
                        sheasyCall.remoteHostIp,
                        authorizationType = AuthorizationType.AUTHORIZED
                    )
                )
                eventDataSource.addEvent(ConnectionEvent(sheasyCall.remoteHostIp))

            }
            completableEmitter.onComplete()
            return@create
        } else {
            val allowedPath = sheasyPrefDataSource.nonInterceptedFolders.any { folderPath ->
                filepath.startsWith(folderPath)
            }

            if (allowedPath) {
                completableEmitter.onComplete()
                return@create
            }

            if (!devicesDataSource.auth.contains(
                    Device(
                        sheasyCall.remoteHostIp,
                        authorizationType = AuthorizationType.AUTHORIZED
                    )
                )
            ) {
                notificationUseCase.showConnectionRequest(sheasyCall.remoteHostIp)
                eventDataSource.addEvent(ConnectionEvent(sheasyCall.remoteHostIp))
                completableEmitter.onError(SheasyError.NotAuthorizedError())
            } else {
                completableEmitter.onComplete()
            }
        }
    }


    override fun handleRoute(route: Route) {
        with(route) {
            intercept(ApplicationCallPipeline.Call) {
                val filepath = call.request.uri

                intercept(call)
                    .doOnError { error ->
                        when (error) {
                            is SheasyError.NotAuthorizedError -> {
                                when (filepath) {

                                    "/" -> launch {
                                        fileDataSource
                                            .getFile(GeneralRouteHandler.CONNECTION_PAGE, true)
                                            .map { it.readBytes() }
                                            .await()
                                            .run {
                                                call.respond(this)
                                            }
                                    }
                                    else -> finish()
                                }

                            }
                        }
                    }
                    .await()


            }

            get("/") {
                fileDataSource
                    .getFile(GeneralRouteHandler.STARTPAGE_PATH, true)
                    .doOnError { error ->
                        if (error is SheasyError) {
                            launch {
                                call.response.debugCorsHeader()
                                call.respond(Resource.error<SheasyError>(error))
                            }
                        }
                    }
                    .map { it.inputStream().readBytes() }
                    .await()
                    .run {
                        call.respond(this)
                    }
            }





            get("/web/{filepath...}") {

                val filepath = call.request.uri.replace("/web/", "web/")

                fileDataSource
                    .getFile(filepath, true)
                    .doOnError { error ->
                        if (error is SheasyError) {
                            launch {
                                call.response.debugCorsHeader()
                                call.respond(Resource.error<SheasyError>(error))
                            }
                        }
                    }
                    .map { it.inputStream().readBytes() }
                    .await()
                    .run {
                        call.respond(this)

                    }
            }



        }


    }


}




