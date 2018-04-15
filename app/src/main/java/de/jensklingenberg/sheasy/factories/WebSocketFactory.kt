package de.jensklingenberg.sheasy.factories

import android.content.Context
import com.squareup.moshi.Moshi
import de.jensklingenberg.sheasy.App
import de.jensklingenberg.sheasy.broReceiver.MySharedMessageBroadcastReceiver
import de.jensklingenberg.sheasy.interfaces.MyHttpServer
import de.jensklingenberg.sheasy.network.MyHttpServerImpl
import de.jensklingenberg.sheasy.network.websocket.IMyWebSocket
import de.jensklingenberg.sheasy.network.websocket.MessageWebsocket
import de.jensklingenberg.sheasy.network.websocket.MyWebSocket
import de.jensklingenberg.sheasy.network.websocket.NotificationWebsocket
import fi.iki.elonen.NanoHTTPD
import fi.iki.elonen.NanoWSD

class WebSocketFactory {



    companion object {
        fun createNotificationWebsocket(context: Context, handshakeRequest: NanoHTTPD.IHTTPSession, httpServerImpl: MyHttpServerImpl, mySharedMessageBroadcastReceiver: MySharedMessageBroadcastReceiver, moshi: Moshi): MyWebSocket {
            return NotificationWebsocket(context, handshakeRequest, httpServerImpl, mySharedMessageBroadcastReceiver, moshi)
        }

        fun createMessageWebsocket(context: Context, handshakeRequest: NanoHTTPD.IHTTPSession, httpServerImpl: MyHttpServerImpl, mySharedMessageBroadcastReceiver: MySharedMessageBroadcastReceiver,  moshi: Moshi):MyWebSocket {
            return MessageWebsocket(context, handshakeRequest, httpServerImpl,mySharedMessageBroadcastReceiver,moshi)
        }

        fun createDefaultWebSocket(context: Context, handshakeRequest: NanoHTTPD.IHTTPSession, httpServerImpl: MyHttpServerImpl): MyWebSocket {
            return MyWebSocket(context, handshakeRequest, httpServerImpl)
        }



        }
}