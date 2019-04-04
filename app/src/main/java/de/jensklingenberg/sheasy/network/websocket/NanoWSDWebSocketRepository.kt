package de.jensklingenberg.sheasy.network.websocket

import android.util.Log
import fi.iki.elonen.NanoHTTPD
import fi.iki.elonen.NanoWSD
import de.jensklingenberg.sheasy.network.SheasyPrefDataSource
import java.io.IOException
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
        try {
            super.start(10000)

        }catch (ioexception:IOException){
            Log.d("THIS",ioexception.message)
        }

    }


}