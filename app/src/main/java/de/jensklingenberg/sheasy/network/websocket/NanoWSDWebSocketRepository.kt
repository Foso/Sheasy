package de.jensklingenberg.sheasy.network.websocket

import fi.iki.elonen.NanoHTTPD
import fi.iki.elonen.NanoWSD
import repository.SheasyPrefDataSource
import javax.inject.Inject

class NanoWSDWebSocketRepository @Inject constructor(sheasyPref: SheasyPrefDataSource) :
    NanoWSD(sheasyPref.webSocketPort),
    NanoWSDWebSocketDataSource {
    override val
            screenShareWebSocketMap
            : HashMap<String, ScreenShareWebSocket>
        get() = hashMapOf()


    var webSocketListener: WebSocketListener? = null

    override fun addListener(webSocketListener: WebSocketListener) {
        this.webSocketListener = webSocketListener

    }

    override fun openWebSocket(handshake: NanoHTTPD.IHTTPSession): NanoWSD.WebSocket {

        return when (val listener = webSocketListener) {
            null -> MyWebSocket(handshake)
            else -> {
                listener.openWebSocket(handshake)
            }
        }


    }

    override fun start() {
        super.start(10000)

    }


}