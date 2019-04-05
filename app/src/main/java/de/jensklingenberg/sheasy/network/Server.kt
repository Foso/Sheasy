package de.jensklingenberg.sheasy.network

import de.jensklingenberg.sheasy.App
import de.jensklingenberg.sheasy.network.ktor.initNetty
import de.jensklingenberg.sheasy.network.routehandler.FileRouteHandler
import de.jensklingenberg.sheasy.network.routehandler.GeneralRouteHandler
import de.jensklingenberg.sheasy.network.websocket.MyWebSocket
import de.jensklingenberg.sheasy.network.websocket.NanoWSDWebSocketDataSource
import de.jensklingenberg.sheasy.network.websocket.NotificationWebSocket
import de.jensklingenberg.sheasy.network.websocket.ScreenShareWebSocket
import de.jensklingenberg.sheasy.network.websocket.ShareWebSocket
import de.jensklingenberg.sheasy.network.websocket.WebSocketListener
import de.jensklingenberg.sheasy.utils.UseCase.VibrationUseCase
import de.jensklingenberg.sheasy.utils.toplevel.runInBackground
import fi.iki.elonen.NanoHTTPD
import fi.iki.elonen.NanoWSD
import io.ktor.server.netty.NettyApplicationEngine
import java.util.concurrent.TimeUnit
import javax.inject.Inject


class Server : WebSocketListener {


    enum class DataDestination {
        SCREENSHARE,SHARE
    }

    @Inject
    lateinit var sheasyPrefDataSource: SheasyPrefDataSource

    @Inject
    lateinit var generalRouteHandler: GeneralRouteHandler

    @Inject
    lateinit var fileRouteHandler: FileRouteHandler

    @Inject
    lateinit var nanoWSDWebSocketDataSource: NanoWSDWebSocketDataSource

    @Inject
    lateinit var vibrationUseCase: VibrationUseCase

    var nettyApplicationEngine: NettyApplicationEngine

    var shareWebSocket : NanoWSD.WebSocket?=null


    val screenShareWebSocketMap = hashMapOf<String, ScreenShareWebSocket>()

    init {
        initializeDagger()
        nettyApplicationEngine = initNetty(
            sheasyPrefDataSource,
            generalRouteHandler,
            fileRouteHandler
        )
        nanoWSDWebSocketDataSource.addListener(this)
    }


    /****************************************** Lifecycle methods  */


    private fun initializeDagger() = App.appComponent.inject(this)

    fun start() {
        runInBackground {

            nettyApplicationEngine.start(wait = true)

        }

        nanoWSDWebSocketDataSource.start()

        vibrationUseCase.vibrate()

    }

    fun stop() {
        nettyApplicationEngine.stop(0L, 0L, TimeUnit.SECONDS)
        nanoWSDWebSocketDataSource.stop()
        screenShareWebSocketMap.values.forEach {
            it.isClosed = true
        }
        screenShareWebSocketMap.clear()
        vibrationUseCase.vibrate()

    }


    /****************************************** Class methods  */


    fun sendData(dataDestination: DataDestination, data: String) {
        when (dataDestination) {

            DataDestination.SCREENSHARE -> {
                screenShareWebSocketMap.values.forEach {
                    it.send(data)
                }
            }
            DataDestination.SHARE->{
                shareWebSocket?.send(data)
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
            DataDestination.SHARE->{
                shareWebSocket?.send(data)
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

            "/notification" -> {
                return NotificationWebSocket(session)
            }


            "/share" -> {
                shareWebSocket= if(shareWebSocket==null){
                    ShareWebSocket(session)
                }else{
                    shareWebSocket
                }
                return shareWebSocket!!
            }
            else -> {
                return MyWebSocket(session)
            }

        }

    }


}

