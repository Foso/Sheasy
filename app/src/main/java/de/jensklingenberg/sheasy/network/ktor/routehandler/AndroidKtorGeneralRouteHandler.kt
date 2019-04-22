package de.jensklingenberg.sheasy.network.ktor.routehandler

import de.jensklingenberg.sheasy.App
import de.jensklingenberg.sheasy.data.FileDataSource
import de.jensklingenberg.sheasy.data.event.EventDataSource
import de.jensklingenberg.sheasy.model.*
import de.jensklingenberg.sheasy.network.SheasyPrefDataSource
import de.jensklingenberg.sheasy.network.extension.ktorApplicationCall
import de.jensklingenberg.sheasy.network.routehandler.GeneralRouteHandler
import de.jensklingenberg.sheasy.data.usecase.NotificationUseCase
import de.jensklingenberg.sheasy.utils.extension.debugCorsHeader
import io.ktor.application.ApplicationCall
import io.ktor.application.ApplicationCallPipeline
import io.ktor.application.call
import io.ktor.request.uri
import io.ktor.response.respond
import io.ktor.response.respondText
import io.ktor.routing.Route
import io.ktor.routing.get
import io.reactivex.Completable
import io.reactivex.Single
import kotlinx.coroutines.launch
import kotlinx.coroutines.rx2.await
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

    init {
        initializeDagger()
    }

    private fun initializeDagger() = App.appComponent.inject(this)

    private fun intercept(call: ApplicationCall): Completable = Completable.create { completableEmitter ->
        val filepath = call.request.uri

        val sheasyCall = call.ktorApplicationCall(filepath)

        if (sheasyPrefDataSource.acceptAllConnections) {
            if (sheasyPrefDataSource.devicesRepository.authorizedDevices.none { device -> device.ip == sheasyCall.remoteHostIp }) {
                sheasyPrefDataSource.devicesRepository.addAuthorizedDevice(
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

            if (!sheasyPrefDataSource.devicesRepository.authorizedDevices.contains(
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

                val filepath = call.request.uri.replace("/web/","web/")

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




