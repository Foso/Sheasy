package de.jensklingenberg.sheasy.network.websocket;

import android.content.Context;
import android.util.Log;

import java.io.IOException;
import java.nio.charset.CharacterCodingException;

import de.jensklingenberg.sheasy.network.MyHttpServerImpl;
import fi.iki.elonen.NanoHTTPD;
import fi.iki.elonen.NanoWSD;


open class MyWebSocket(context: Context, internal var httpSession: NanoHTTPD.IHTTPSession, internal var httpServerImpl: MyHttpServerImpl) : NanoWSD.WebSocket(httpSession) {

    override fun onOpen() {
        Log.d(TAG, "onOpen: ")
        httpServerImpl.connections.add(this)

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
        this.httpServerImpl.connections.remove(this)
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