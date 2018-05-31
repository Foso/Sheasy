package de.jensklingenberg.sheasy.network.websocket


import android.content.Context
import com.squareup.moshi.Moshi
import de.jensklingenberg.sheasy.App
import de.jensklingenberg.sheasy.BuildConfig
import de.jensklingenberg.sheasy.broReceiver.MySharedMessageBroadcastReceiver
import de.jensklingenberg.sheasy.enums.WebsocketCommand
import de.jensklingenberg.sheasy.factories.WebSocketFactory
import de.jensklingenberg.sheasy.interfaces.MyHttpServer
import de.jensklingenberg.sheasy.utils.BundledNotificationHelper
import fi.iki.elonen.NanoHTTPD
import fi.iki.elonen.NanoWSD
import java.util.*
import javax.inject.Inject


interface NanoWsdWebSocketListener {
    fun onNotificationWebSocketRequest()
}


class MyHttpServerImpl : NanoWSD, MyHttpServer {

    @Inject
    lateinit var moshi: Moshi

    @Inject
    lateinit var mySharedMessageBroadcastReceiver: MySharedMessageBroadcastReceiver

    init {
        initializeDagger()
    }

    private fun initializeDagger() = App.appComponent.inject(this)


    var nanoWsdWebSocketListener: NanoWsdWebSocketListener? = null

    override fun addWebSocketListenr(nanoWsdWebSocketListener: NanoWsdWebSocketListener) {
        this.nanoWsdWebSocketListener = nanoWsdWebSocketListener

    }

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
        super.stop()

    }

    override fun openWebSocket(handshake: NanoHTTPD.IHTTPSession): NanoWSD.WebSocket {
        var uri = handshake.uri
        uri = uri.replaceFirst("/", "", true)

        val websockCommand = WebsocketCommand.get(uri)
        when (websockCommand) {
            WebsocketCommand.NOTIFICATION -> {
                nanoWsdWebSocketListener?.onNotificationWebSocketRequest()

                return WebSocketFactory.createNotificationWebsocket(
                    context,
                    handshake,
                    this
                )

            }
            WebsocketCommand.MESSAGE -> {
                return WebSocketFactory.createMessageWebsocket(
                    context,
                    handshake,
                    this
                )

            }
            WebsocketCommand.SCREENSHARE -> {
                val screenSharWebsocket = WebSocketFactory.createScreenshareWebsocket(
                    context,
                    handshake,
                    this
                )
                connections.add(screenSharWebsocket)
                return screenSharWebsocket
            }
        }

        return WebSocketFactory.createDefaultWebSocket(handshake, this)
    }


}

