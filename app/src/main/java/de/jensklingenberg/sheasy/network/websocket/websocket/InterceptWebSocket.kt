package de.jensklingenberg.sheasy.network.websocket.websocket

import android.annotation.SuppressLint
import android.util.Log
import com.squareup.moshi.Moshi
import de.jensklingenberg.sheasy.App
import fi.iki.elonen.NanoHTTPD
import fi.iki.elonen.NanoWSD
import io.reactivex.Observable
import io.reactivex.schedulers.Schedulers
import java.io.IOException
import java.util.concurrent.TimeUnit
import javax.inject.Inject


open class InterceptWebSocket(handshake: NanoHTTPD.IHTTPSession?) : NanoWSD.WebSocket(handshake) {



    var isClosed = false
    val TAG = javaClass.simpleName


    override fun onClose(
        code: NanoWSD.WebSocketFrame.CloseCode,
        reason: String,
        initiatedByRemote: Boolean
    ) {
        isClosed = true

    }

    override fun onMessage(message: NanoWSD.WebSocketFrame) {


    }

    override fun onPong(pong: NanoWSD.WebSocketFrame) {
        Log.d(TAG, "onPong: ")
    }

    override fun onException(exception: IOException) {
        Log.d(TAG, "onException: " + exception.message)
    }


    override fun onOpen() {

    }




}
