package de.jensklingenberg.sheasy.network;

import android.content.Context;
import de.jensklingenberg.sheasy.handler.WebRequestHandler
import de.jensklingenberg.sheasy.network.websocket.MyWebSocket
import de.jensklingenberg.sheasy.network.websocket.NotificationWebsocket
import de.jensklingenberg.sheasy.enums.ApiCommand
import de.jensklingenberg.sheasy.handler.*


import java.util.ArrayList;

import fi.iki.elonen.NanoHTTPD;
import fi.iki.elonen.NanoWSD;
import de.jensklingenberg.sheasy.enums.WebsocketCommand
import de.jensklingenberg.sheasy.extension.remove
import de.jensklingenberg.sheasy.network.websocket.MessageWebsocket
import de.jensklingenberg.sheasy.utils.BundledNotificationHelper


class MyHttpServer : NanoWSD {

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
        lateinit var instance: MyHttpServer
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

    public override fun serveHttp(session: NanoHTTPD.IHTTPSession): Response? {
        var uri = session.uri
        
        if (uri.startsWith("/$API_V1/")) {
            uri = uri.remove("/$API_V1/")
            val split = uri.split("/")
            val apiCommand = ApiCommand.get(split.first())

            when (apiCommand) {

                ApiCommand.Apps -> {
                    return AppsRequestHandler.handle(context, session.uri)
                }
                ApiCommand.media -> {
                    return MediaRequestHandler.handle(context, session.uri,session)
                }
                ApiCommand.Intent -> {
                    return IntentRequestHandler.handle(context, session)
                }
                ApiCommand.DEVICE -> {
                    return DeviceRequestHandler.handle(context, session.uri)
                }
                ApiCommand.WEB -> {
                    return WebRequestHandler.handle(context, session.uri)
                }
                ApiCommand.Download -> {
                    return WebRequestHandler.handle(context, session.uri)
                }
                ApiCommand.FILE -> {
                    return FileRequestHandler.handleRequest(session)
                }
                ApiCommand.SHARE -> {
                    return ShareRequestHandler.handle(session.uri)
                }

                ApiCommand.CONTACTS -> {
                    return ContactsRequestHandler.handle(context, session.uri)
                }
            }
        } else {
            uri = uri.replaceFirst("/", "", true)
            return WebRequestHandler.handle(context, uri)


        }

        return NanoHTTPD.newFixedLengthResponse("Command ${uri} not found")
    }


}

