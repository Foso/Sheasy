package de.jensklingenberg.sheasy.network.websocket

import android.util.Log
import de.jensklingenberg.sheasy.App
import de.jensklingenberg.sheasy.data.DevicesDataSource
import de.jensklingenberg.sheasy.network.SheasyPrefDataSource
import de.jensklingenberg.sheasy.network.websocket.websocket.*
import fi.iki.elonen.NanoHTTPD
import fi.iki.elonen.NanoWSD
import java.io.IOException
import javax.inject.Inject

class NanoWSDWebSocketRepository @Inject constructor(sheasyPref: SheasyPrefDataSource) :
    NanoWSD(sheasyPref.webSocketPort),
    NanoWSDWebSocketDataSource {


    override var shareWebSocket: SheasyWebSocket? = null

    @Inject
    lateinit var sheasyPrefDataSource: SheasyPrefDataSource

    @Inject
    lateinit var devicesDataSource: DevicesDataSource


    init {
        initializeDagger()
    }

    private fun initializeDagger() = App.appComponent.inject(this)

    private fun shouldIntercept(session: IHTTPSession):Boolean{
        if (sheasyPrefDataSource.acceptAllConnections) {
            if (devicesDataSource.auth.none { device -> device.ip == session.remoteIpAddress }) {
                return true
            }
        }

        return false
    }

    override fun openWebSocket(session: IHTTPSession): WebSocket {

        if (shouldIntercept(session)) {
            //TODO: Find out how return nothing, when intercepted
            return InterceptWebSocket(session)

        }else{
            when (session.uri) {


                "/notification" -> {
                    return NotificationWebSocket(session)
                }


                "/share" -> {
                    if (shareWebSocket == null) {
                        shareWebSocket = ShareWebSocket(session)
                        shareWebSocket?.let {
                            return it as WebSocket
                        }
                    } else {
                        shareWebSocket?.let {
                            if (it.isOpen()) {
                                return  it as WebSocket
                            } else {
                                shareWebSocket =
                                    ShareWebSocket(session)
                                shareWebSocket?.let {
                                    return  it as WebSocket
                                }
                            }
                        }

                    }

                    shareWebSocket = if (shareWebSocket == null) {
                        ShareWebSocket(session)
                    } else {
                        shareWebSocket
                    }
                    return shareWebSocket as? WebSocket ?: ShareWebSocket(session)
                }
                else -> {
                    return MyWebSocket(session)
                }


            }
        }
    }

    override fun start() {
        try {
            super.start(10000)

        } catch (ioexception: IOException) {
            Log.d("THIS", ioexception.message)
        }

    }

    override fun stop() {


        super.stop()
    }


}