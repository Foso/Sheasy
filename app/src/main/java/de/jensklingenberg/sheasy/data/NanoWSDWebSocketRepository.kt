package de.jensklingenberg.sheasy.data

import de.jensklingenberg.sheasy.network.MyWebSocket
import de.jensklingenberg.sheasy.network.ScreenShareWebSocket
import de.jensklingenberg.sheasy.network.WebSocketListener
import fi.iki.elonen.NanoHTTPD
import fi.iki.elonen.NanoWSD
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