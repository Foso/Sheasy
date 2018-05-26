package de.jensklingenberg.sheasy.factories

import android.content.Context
import de.jensklingenberg.sheasy.interfaces.MyHttpServer
import de.jensklingenberg.sheasy.network.websocket.MyHttpServerImpl

class ServerFactory {


    companion object {
        fun createHTTPServer(httpServerService: Context): MyHttpServer {
            return MyHttpServerImpl(httpServerService)
        }

    }
}