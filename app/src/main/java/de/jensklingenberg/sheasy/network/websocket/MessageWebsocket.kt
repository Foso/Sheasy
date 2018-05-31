package de.jensklingenberg.sheasy.network.websocket

import android.content.Context
import android.content.IntentFilter
import com.squareup.moshi.Moshi
import de.jensklingenberg.sheasy.App
import de.jensklingenberg.sheasy.broReceiver.MySharedMessageBroadcastReceiver
import de.jensklingenberg.sheasy.broReceiver.MySharedMessageBroadcastReceiver.Companion.MESSAGE
import de.jensklingenberg.sheasy.interfaces.NotifyClientEventListener
import de.jensklingenberg.sheasy.model.NotificationResponse
import de.jensklingenberg.sheasy.utils.extension.toJson
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
class MessageWebsocket(
    context: Context,
    handshakeRequest: NanoHTTPD.IHTTPSession,
    httpServerImpl: MyHttpServerImpl
) : MyWebSocket(handshakeRequest, httpServerImpl), NotifyClientEventListener {

    @Inject
    lateinit var msbr: MySharedMessageBroadcastReceiver

    @Inject
    lateinit var moshi: Moshi


    private fun initializeDagger() = App.appComponent.inject(this)


    override fun onMessageForClientReceived(notificationResponse: NotificationResponse) {

        runInBackground {
            send(moshi.toJson(notificationResponse))
        }
    }


    init {
        initializeDagger()
        val filter = IntentFilter(MESSAGE)
        msbr.notifyClientEventListener = this
        context.registerReceiver(msbr, filter)
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
            .subscribe { result ->
                //Use result for something
            }


    }


}