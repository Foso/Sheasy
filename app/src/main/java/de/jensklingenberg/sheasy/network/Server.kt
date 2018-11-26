package de.jensklingenberg.sheasy.network

import de.jensklingenberg.sheasy.App
import de.jensklingenberg.sheasy.data.SheasyPrefDataSource
import de.jensklingenberg.sheasy.data.file.FileDataSource
import de.jensklingenberg.sheasy.network.routes.apps
import de.jensklingenberg.sheasy.network.routes.file
import de.jensklingenberg.sheasy.network.routes.general
import de.jensklingenberg.sheasy.utils.NotificationUtils
import de.jensklingenberg.sheasy.utils.toplevel.runInBackground
import fi.iki.elonen.NanoWSD
import io.ktor.application.ApplicationCallPipeline
import io.ktor.application.call
import io.ktor.application.install
import io.ktor.features.*
import io.ktor.gson.gson
import io.ktor.routing.route
import io.ktor.routing.routing
import io.ktor.server.engine.embeddedServer
import io.ktor.server.netty.Netty
import java.util.concurrent.TimeUnit
import javax.inject.Inject


class Server(
    val sheasyPref: SheasyPrefDataSource
) : NanoWSD(sheasyPref.webSocketPort) {

    @Inject
    lateinit var notificationUtils: NotificationUtils

    @Inject
    lateinit var fileRepository: FileDataSource

    init {
        initializeDagger()
    }

    private fun initializeDagger() = App.appComponent.inject(this)


    override fun openWebSocket(handshake: IHTTPSession?): WebSocket {
        return MyWebSocket(handshake)
    }

    override fun start() {
        runInBackground {
            netty.start(wait = true)
        }

        super.start(10000)
    }

    override fun stop() {
        netty.stop(0L, 0L, TimeUnit.SECONDS)

        super.stop()
    }


    val netty = embeddedServer(Netty, sheasyPref.port) {

        install(ContentNegotiation) {
            gson {
                setPrettyPrinting()
            }
        }

        install(Compression) {
            gzip()
        }
        install(PartialContent) {
            maxRangeCount = 10
        }


        routing {


            route("") {

                intercept(ApplicationCallPipeline.Call) {

                    if (sheasyPref.authorizedDevices.contains(call.request.origin.remoteHost)) {

                    } else {
                        val cal = call
                        notificationUtils.showConnectionRequest(call.request.origin.remoteHost)
                        sheasyPref.addAuthorizedDevice(call.request.origin.remoteHost)

                    }
                }

                general(fileRepository)

                route(sheasyPref.APIV1) {
                    apps(fileRepository)
                    file(fileRepository)

                }
            }


        }
    }


}

