package de.jensklingenberg.sheasy.network.websocket;

import android.content.Context;
import android.util.Log;
import com.squareup.moshi.Moshi

import java.io.IOException;
import java.nio.charset.CharacterCodingException;
import java.util.concurrent.TimeUnit;

import de.jensklingenberg.sheasy.network.MyHttpServer;
import de.jensklingenberg.sheasy.model.NotificationResponse
import fi.iki.elonen.NanoHTTPD;
import fi.iki.elonen.NanoWSD;
import io.reactivex.Observable;
import io.reactivex.schedulers.Schedulers;


open class MyWebSocket(context: Context, internal var httpSession: NanoHTTPD.IHTTPSession, internal var httpServer: MyHttpServer) : NanoWSD.WebSocket(httpSession) {

    override fun onOpen() {
        Log.d(TAG, "onOpen: ")
        httpServer.connections.add(this)

        try {
            val pingframe = NanoWSD.WebSocketFrame(NanoWSD.WebSocketFrame.OpCode.Ping, false, "")

            ping(pingframe.binaryPayload)
            Log.d(TAG, "onOpen: ping")

        } catch (e: CharacterCodingException) {
            e.printStackTrace()
        } catch (e: IOException) {
            e.printStackTrace()
        }


    }


    override fun onClose(code: NanoWSD.WebSocketFrame.CloseCode, reason: String, initiatedByRemote: Boolean) {
        this.httpServer.connections.remove(this)
    }

    override fun onMessage(message: NanoWSD.WebSocketFrame) {
        Log.d(TAG, message.textPayload.toString())
        Log.d(TAG, "onMessage: ")
        message.setUnmasked()

    }

    override fun onPong(pong: NanoWSD.WebSocketFrame) {
        Log.d(TAG, "onPong: ")
    }

    override fun onException(exception: IOException) {
        Log.d(TAG, "onException: " + exception.message)
    }

    companion object {
        val TAG = "ddd"
    }
}