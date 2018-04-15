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
import de.jensklingenberg.sheasy.interfaces.MyHttpServer
import de.jensklingenberg.sheasy.network.websocket.MessageWebsocket
import de.jensklingenberg.sheasy.utils.BundledNotificationHelper
import de.jensklingenberg.sheasy.utils.NotUtils


class MyHttpServerImpl : NanoWSD, MyHttpServer {

    private val context: Context
    var main: Context? = null
    val connections: ArrayList<MyWebSocket>

    constructor(context: Context) : super(PORT) {
        this.context = context
        this.connections = arrayListOf()
        main = context
        this.main = context
        instance = this
        BundledNotificationHelper(context).generateBundle(context)
    }

    companion object {
        val PORT = 8765
        val ACTION_SHARE = "seven.notificationlistenerdemo.NLSCONTROL"

        const val API_V1 = "api/v1"
        lateinit var instance: MyHttpServerImpl
    }

    override fun openWebSocket(handshake: NanoHTTPD.IHTTPSession): NanoWSD.WebSocket {
        var uri = handshake.uri
        uri = uri.replaceFirst("/", "", true)

        val split = uri.split("/")
        val websockCommand = WebsocketCommand.get(split[0])
        when (websockCommand) {
            WebsocketCommand.NOTIFICATION -> {
                return NotificationWebsocket(context, handshake, this)

            }
            WebsocketCommand.MESSAGE -> {
                return MessageWebsocket(context, handshake, this)

            }
        }

        return MyWebSocket(context, handshake, this)
    }

    override fun serveHttp(session: IHTTPSession): Response? {

        val connectedIps = listOf("192.168.178.32")
        if (!connectedIps.contains(session.remoteIpAddress)) {
            NotUtils.showConnectionRequest(context, session.remoteIpAddress)
           // return NanoHTTPD.newFixedLengthResponse("Not Allowed")
        }

        return RequestHandlerFactory.create(context, session, App.instance)
    }


}

