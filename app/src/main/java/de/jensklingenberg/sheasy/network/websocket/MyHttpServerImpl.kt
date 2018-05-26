package de.jensklingenberg.sheasy.network.websocket


import android.content.Context
import de.jensklingenberg.sheasy.App
import de.jensklingenberg.sheasy.BuildConfig
import de.jensklingenberg.sheasy.enums.WebsocketCommand
import de.jensklingenberg.sheasy.factories.WebSocketFactory
import de.jensklingenberg.sheasy.interfaces.MyHttpServer
import de.jensklingenberg.sheasy.utils.BundledNotificationHelper
import fi.iki.elonen.NanoHTTPD
import fi.iki.elonen.NanoWSD
import java.util.*


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
        val PORT = BuildConfig.WEBSOCKET_PORT

    }

    override fun stop() {


    }

    override fun openWebSocket(handshake: NanoHTTPD.IHTTPSession): NanoWSD.WebSocket {
        var uri = handshake.uri
        uri = uri.replaceFirst("/", "", true)

        val split = uri.split("/")
        val websockCommand = WebsocketCommand.get(split[1])
        when (websockCommand) {
            WebsocketCommand.NOTIFICATION -> {
                return WebSocketFactory.createNotificationWebsocket(
                    context,
                    handshake,
                    this,
                    App.instance.mySharedMessageBroadcastReceiver,
                    App.instance.moshi
                )

            }
            WebsocketCommand.MESSAGE -> {
                return WebSocketFactory.createMessageWebsocket(
                    context,
                    handshake,
                    this,
                    App.instance.mySharedMessageBroadcastReceiver,
                    App.instance.moshi
                )

            }
        }

        return WebSocketFactory.createDefaultWebSocket(context, handshake, this)
    }




}

