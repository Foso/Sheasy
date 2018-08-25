package de.jensklingenberg.sheasy.network.websocket

import android.content.Context
import android.content.IntentFilter
import com.squareup.moshi.Moshi
import de.jensklingenberg.sheasy.App
import de.jensklingenberg.sheasy.broReceiver.MySharedMessageBroadcastReceiver
import de.jensklingenberg.sheasy.broReceiver.MySharedMessageBroadcastReceiver.Companion.EVENT_SCREENSHARE
import de.jensklingenberg.sheasy.interfaces.OnScreenShareEventListener
import de.jensklingenberg.sheasy.utils.toplevel.runInBackground
import fi.iki.elonen.NanoHTTPD
import fi.iki.elonen.NanoWSD
import io.reactivex.Observable
import io.reactivex.schedulers.Schedulers
import java.util.concurrent.TimeUnit
import javax.inject.Inject

/**
 * Created by jens on 18/2/18.
 */
class ScreenSharWebsocket(
    context: Context,
    handshakeRequest: NanoHTTPD.IHTTPSession,
    httpServerImpl: MyHttpServerImpl

) : MyWebSocket(handshakeRequest, httpServerImpl), OnScreenShareEventListener {

    @Inject
    lateinit var moshi: Moshi

    @Inject
    lateinit var mySharedMessageBroadcastReceiver: MySharedMessageBroadcastReceiver


    init {
        initializeDagger()
    }

    private fun initializeDagger() = App.appComponent.inject(this)


    override fun onDataForClientReceived(notificationResponse: String) {

        runInBackground {
            if (isClosed == false) {
                send(notificationResponse)

            }
        }
    }

    init {
        val filter = IntentFilter(EVENT_SCREENSHARE)
        mySharedMessageBroadcastReceiver.onScreenShareEventListener = this
        context.registerReceiver(mySharedMessageBroadcastReceiver, filter)
    }

    override fun onOpen() {
        super.onOpen()
        startRunner()

    }

    private fun startRunner() {

        Observable.fromCallable {
            val pingframe = NanoWSD.WebSocketFrame(NanoWSD.WebSocketFrame.OpCode.Ping, false, "")
            ping(pingframe.binaryPayload)

            true
        }.delay(1, TimeUnit.SECONDS)
            .repeatUntil({ isClosed })
            .subscribeOn(Schedulers.newThread())
            .observeOn(Schedulers.newThread())
            .subscribe {}


    }


}