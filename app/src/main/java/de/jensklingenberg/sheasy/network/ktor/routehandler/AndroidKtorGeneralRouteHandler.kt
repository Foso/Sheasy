package de.jensklingenberg.sheasy.network.ktor.routehandler

import de.jensklingenberg.sheasy.App
import de.jensklingenberg.sheasy.data.FileDataSource
import de.jensklingenberg.sheasy.data.event.EventDataSource
import de.jensklingenberg.sheasy.model.*
import de.jensklingenberg.sheasy.network.SheasyPrefDataSource
import de.jensklingenberg.sheasy.network.extension.ktorApplicationCall
import de.jensklingenberg.sheasy.network.routehandler.GeneralRouteHandler
import de.jensklingenberg.sheasy.data.usecase.NotificationUseCase
import io.ktor.application.ApplicationCall
import io.ktor.application.ApplicationCallPipeline
import io.ktor.application.call
import io.ktor.response.respond
import io.ktor.routing.Route
import io.ktor.routing.get
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

    private fun intercept(call: ApplicationCall): Single<Boolean> {


        return Single.create<Boolean> { singleEmitter ->

            val filepath = "web/" + call.parameters.entries().firstOrNull { it.key == "filepath" }?.value?.joinToString("/")

            val sheasyCall = call.ktorApplicationCall(filepath)

            if (sheasyPrefDataSource.acceptAllConnections) {
                if (sheasyPrefDataSource.devicesRepository.authorizedDevices.none { device -> device.ip == sheasyCall.remoteHostIp }) {
                    sheasyPrefDataSource.devicesRepository.addAuthorizedDevice(
                        Device(
                            sheasyCall.remoteHostIp,
                            authorizationType = AuthorizationType.AUTHORIZED
                        )
                    )
                    eventDataSource.addEvent(ConnectionEvent( sheasyCall.remoteHostIp))

                }
                singleEmitter.onSuccess(false)

            }

            val allowedPath = sheasyPrefDataSource.nonInterceptedFolders.any { folderPath ->
                sheasyCall.requestedApiPath.startsWith(folderPath)
            }

            if (allowedPath) {
                singleEmitter.onSuccess(false)
            }


            if (!sheasyPrefDataSource.devicesRepository.authorizedDevices.contains(
                    Device(
                        sheasyCall.remoteHostIp,
                        authorizationType = AuthorizationType.AUTHORIZED
                    )
                )
            ) {
                notificationUseCase.showConnectionRequest(sheasyCall.remoteHostIp)
                eventDataSource.addEvent(ConnectionEvent( sheasyCall.remoteHostIp))
                singleEmitter.onError(SheasyError.NotAuthorizedError())



            } else {

                singleEmitter.onSuccess(false)

            }


        }




    }

    override fun handleRoute(route: Route) {
        with(route){
            intercept(ApplicationCallPipeline.Call) {


                val filepath = "web/" + call.parameters.entries().firstOrNull { it.key == "filepath" }?.value?.joinToString("/")

                intercept(call)
                    .doOnError {error->
                        when (error) {
                            is SheasyError.NotAuthorizedError -> {
                                launch {
                                    if (!filepath.contains("web/connection/")) {
                                        fileDataSource
                                            .getFile(GeneralRouteHandler.CONNECTION_PAGE,true)
                                            .map { it.readBytes() }
                                            .await()
                                            .run {
                                                call.respond(this)
                                            }
                                    }
                                }
                            }
                        }
                    }
                    .await()


            }

            get("/") {
                fileDataSource
                    .getFile(GeneralRouteHandler.STARTPAGE_PATH,true)
                    .map { it.readBytes() }
                    .await()
                    .run {
                        call.respond(this)
                    }
            }

            get("web/{filepath...}") {
                val filepath = "web/" + call.parameters.entries().first { it.key == "filepath" }.value.joinToString("/")

                fileDataSource
                    .getFile(filepath,true)
                    .map { it.readBytes() }
                    .await()
                    .run {
                        call.respond(this)

                    }
            }
        }


    }




}




