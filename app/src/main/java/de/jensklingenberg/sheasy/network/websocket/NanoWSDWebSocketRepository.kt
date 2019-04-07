package de.jensklingenberg.sheasy.network.websocket

import android.util.Log
import de.jensklingenberg.sheasy.network.SheasyPrefDataSource
import fi.iki.elonen.NanoHTTPD
import fi.iki.elonen.NanoWSD
import java.io.IOException
import javax.inject.Inject

class NanoWSDWebSocketRepository @Inject constructor(sheasyPref: SheasyPrefDataSource) :
    NanoWSD(sheasyPref.webSocketPort),
    NanoWSDWebSocketDataSource {


    override var shareWebSocket: NanoWSD.WebSocket? = null


    override val
            screenShareWebSocketMap
            : HashMap<String, ScreenShareWebSocket>
        get() = hashMapOf()


    var webSocketListener: WebSocketListener? = null

    override fun addListener(webSocketListener: WebSocketListener) {
        this.webSocketListener = webSocketListener

    }

    override fun serveHttp(session: IHTTPSession?): Response {

        return super.serveHttp(session)
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
                if (shareWebSocket == null) {
                    shareWebSocket = ShareWebSocket(session)
                    shareWebSocket?.let {
                        return it
                    }
                } else {
                    shareWebSocket?.let {
                        if(it.isOpen){
                            return it
                        }else{
                            shareWebSocket = ShareWebSocket(session)
                            shareWebSocket?.let {
                                return it
                            }
                        }
                    }

                }

                shareWebSocket = if (shareWebSocket == null) {
                    ShareWebSocket(session)
                } else {
                    shareWebSocket
                }
                return shareWebSocket ?: ShareWebSocket(session)
            }
            else -> {
                return MyWebSocket(session)
            }


        }


    }

    override fun start() {
        try {
            super.start(10000)

        } catch (ioexception: IOException) {
            Log.d("THIS", ioexception.message)
        }

    }

    override fun stop() {
        screenShareWebSocketMap.values.forEach {
            it.isClosed = true
        }
        screenShareWebSocketMap.clear()
        super.stop()
    }


}