package de.jensklingenberg.sheasy.factories

import android.content.Context
import com.squareup.moshi.Moshi
import de.jensklingenberg.sheasy.broReceiver.MySharedMessageBroadcastReceiver
import de.jensklingenberg.sheasy.network.websocket.*
import fi.iki.elonen.NanoHTTPD

class WebSocketFactory {


    companion object {
        fun createNotificationWebsocket(
            context: Context,
            handshakeRequest: NanoHTTPD.IHTTPSession,
            httpServerImpl: MyHttpServerImpl
        ): MyWebSocket {
            return NotificationWebsocket(
                context,
                handshakeRequest,
                httpServerImpl
            )
        }

        fun createScreenshareWebsocket(
            context: Context,
            handshakeRequest: NanoHTTPD.IHTTPSession,
            httpServerImpl: MyHttpServerImpl
        ): MyWebSocket {
            return ScreenSharWebsocket(
                context,
                handshakeRequest,
                httpServerImpl
            )
        }


        fun createMessageWebsocket(
            context: Context,
            handshakeRequest: NanoHTTPD.IHTTPSession,
            httpServerImpl: MyHttpServerImpl
        ): MyWebSocket {
            return MessageWebsocket(
                context,
                handshakeRequest,
                httpServerImpl
            )
        }

        fun createDefaultWebSocket(
            handshakeRequest: NanoHTTPD.IHTTPSession,
            httpServerImpl: MyHttpServerImpl
        ): MyWebSocket {
            return MyWebSocket(handshakeRequest, httpServerImpl)
        }


    }
}