package de.jensklingenberg.sheasy.network

import de.jensklingenberg.sheasy.App
import de.jensklingenberg.sheasy.data.NanoWSDWebSocketDataSource
import de.jensklingenberg.sheasy.data.SheasyPrefDataSource
import de.jensklingenberg.sheasy.data.file.FileDataSource
import de.jensklingenberg.sheasy.network.routes.apps
import de.jensklingenberg.sheasy.network.routes.file
import de.jensklingenberg.sheasy.network.routes.general
import de.jensklingenberg.sheasy.utils.NotificationUtils
import de.jensklingenberg.sheasy.utils.toplevel.runInBackground
import fi.iki.elonen.NanoHTTPD
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
import de.jensklingenberg.sheasy.model.Device
import java.util.concurrent.TimeUnit
import javax.inject.Inject


class Server : WebSocketListener {

    enum class DataDestination {
        SCREENSHARE
    }

    @Inject
    lateinit var sheasyPref: SheasyPrefDataSource

    @Inject
    lateinit var notificationUtils: NotificationUtils

    @Inject
    lateinit var fileDataSource: FileDataSource

    @Inject
    lateinit var nanoWSDWebSocketDataSource: NanoWSDWebSocketDataSource

    val screenShareWebSocketMap = hashMapOf<String, ScreenShareWebSocket>()


    init {
        initializeDagger()
        nanoWSDWebSocketDataSource.addListener(this)
    }

    private fun initializeDagger() = App.appComponent.inject(this)

    fun sendData(dataDestination: DataDestination, data: String) {
        when (dataDestination) {

            DataDestination.SCREENSHARE -> {
                screenShareWebSocketMap.values.forEach {
                    it.send(data)
                }
            }
        }
    }

    fun sendData(dataDestination: DataDestination, data: ByteArray) {
        when (dataDestination) {

            DataDestination.SCREENSHARE -> {
                screenShareWebSocketMap.values.forEach {
                    it.send(data)

                }
            }
        }
    }


    fun start() {
        runInBackground {
            netty.start(wait = true)
        }
        nanoWSDWebSocketDataSource.start()

    }

    fun stop() {
        netty.stop(0L, 0L, TimeUnit.SECONDS)
        nanoWSDWebSocketDataSource.stop()
        screenShareWebSocketMap.values.forEach {
            it.isClosed = true
        }
        screenShareWebSocketMap.clear()

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

                    if (sheasyPref.authorizedDevices.contains(Device(call.request.origin.remoteHost))) {

                    } else {
                        notificationUtils.showConnectionRequest(call.request.origin.remoteHost)
                        val device = Device(call.request.origin.remoteHost)
                        sheasyPref.addAuthorizedDevice(device)

                    }
                }

                general(fileDataSource)

                route(sheasyPref.APIV1) {
                    apps(fileDataSource)
                    file(fileDataSource)

                }
            }


        }
    }



    override fun openWebSocket(session: NanoHTTPD.IHTTPSession): NanoWSD.WebSocket {
        when (session.uri) {
            "/screenshare" -> {

                if (screenShareWebSocketMap.containsKey(session.remoteIpAddress)) {
                    return screenShareWebSocketMap[session.remoteIpAddress]!!
                } else {
                    val screenShareWebSocket = ScreenShareWebSocket(session)
                    screenShareWebSocketMap[session.remoteIpAddress] = screenShareWebSocket
                    return screenShareWebSocket
                }

            }
            else -> {
                return MyWebSocket(session)
            }

        }

    }


}

