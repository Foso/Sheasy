package de.jensklingenberg.sheasy.network;

import android.content.Context;
import de.jensklingenberg.sheasy.App
import de.jensklingenberg.sheasy.network.websocket.MyWebSocket
import de.jensklingenberg.sheasy.network.websocket.NotificationWebsocket
import de.jensklingenberg.sheasy.handler.*


import java.util.ArrayList;

import fi.iki.elonen.NanoHTTPD;
import fi.iki.elonen.NanoWSD;
import de.jensklingenberg.sheasy.enums.WebsocketCommand
import de.jensklingenberg.sheasy.factories.WebSocketFactory
import de.jensklingenberg.sheasy.interfaces.MyHttpServer
import de.jensklingenberg.sheasy.network.websocket.MessageWebsocket
import de.jensklingenberg.sheasy.utils.BundledNotificationHelper
import de.jensklingenberg.sheasy.utils.NotifUtils


class MyHttpServerImpl : NanoWSD, MyHttpServer {

    private val context: Context
    var main: Context? = null
    val connections: ArrayList<MyWebSocket>

    constructor(context: Context) : super(PORT) {
        this.context = context
        this.connections = arrayListOf()
        main = context
        this.main = context
        BundledNotificationHelper(context).generateBundle(context)
    }

    companion object {
        val PORT = 8765

        const val API_V1 = "api/v1"
    }

    override fun openWebSocket(handshake: NanoHTTPD.IHTTPSession): NanoWSD.WebSocket {
        var uri = handshake.uri
        uri = uri.replaceFirst("/", "", true)

        val split = uri.split("/")
        val websockCommand = WebsocketCommand.get(split[1])
        when (websockCommand) {
            WebsocketCommand.NOTIFICATION -> {
                return WebSocketFactory.createNotificationWebsocket(context, handshake, this,App.instance.mySharedMessageBroadcastReceiver,App.instance.moshi)

            }
            WebsocketCommand.MESSAGE -> {
                return WebSocketFactory.createMessageWebsocket(context, handshake, this,App.instance.mySharedMessageBroadcastReceiver,App.instance.moshi)

            }
        }

        return WebSocketFactory.createDefaultWebSocket(context, handshake, this)
    }

    override fun serveHttp(session: IHTTPSession): Response? {

        val connectedIps = listOf("192.168.178.32")
        if (!connectedIps.contains(session.remoteIpAddress)) {
            NotifUtils.showConnectionRequest(context, session.remoteIpAddress)
           // return NanoHTTPD.newFixedLengthResponse("Not Allowed")
        }

        return RequestHandlerFactory.create(context, session, App.instance)
    }


}

