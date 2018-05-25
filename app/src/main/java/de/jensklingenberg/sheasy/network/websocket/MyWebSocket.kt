package de.jensklingenberg.sheasy.network.websocket;

import android.content.Context
import android.util.Log
import de.jensklingenberg.sheasy.network.MyHttpServerImpl
import fi.iki.elonen.NanoHTTPD
import fi.iki.elonen.NanoWSD
import java.io.IOException
import java.nio.charset.CharacterCodingException


interface IMyWebSocket {
    fun onOpen()
    fun onClose(code: NanoWSD.WebSocketFrame.CloseCode, reason: String, initiatedByRemote: Boolean)
    fun onMessage(message: NanoWSD.WebSocketFrame)
    fun onPong(pong: NanoWSD.WebSocketFrame)
    fun onException(exception: IOException)
}

open class MyWebSocket(
    context: Context,
    internal var httpSession: NanoHTTPD.IHTTPSession,
    internal var httpServerImpl: MyHttpServerImpl
) : NanoWSD.WebSocket(httpSession), IMyWebSocket {

    var isClosed = false


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


    override fun onClose(
        code: NanoWSD.WebSocketFrame.CloseCode,
        reason: String,
        initiatedByRemote: Boolean
    ) {
        isClosed = true
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