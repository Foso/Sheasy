package de.jensklingenberg.sheasy.network.websocket

import android.content.Context
import android.content.IntentFilter
import com.squareup.moshi.Moshi
import de.jensklingenberg.sheasy.broReceiver.MySharedMessageBroadcastReceiver
import de.jensklingenberg.sheasy.broReceiver.MySharedMessageBroadcastReceiver.Companion.MESSAGE
import de.jensklingenberg.sheasy.interfaces.NotifyClientEventListener
import de.jensklingenberg.sheasy.model.NotificationResponse
import de.jensklingenberg.sheasy.toplevel.runInBackground
import fi.iki.elonen.NanoHTTPD
import fi.iki.elonen.NanoWSD
import io.reactivex.Observable
import io.reactivex.schedulers.Schedulers
import java.util.concurrent.TimeUnit

/**
 * Created by jens on 18/2/18.
 */
class MessageWebsocket(
    context: Context,
    handshakeRequest: NanoHTTPD.IHTTPSession,
    httpServerImpl: MyHttpServerImpl,
    mySharedMessageBroadcastReceiver: MySharedMessageBroadcastReceiver,
    val moshi: Moshi
) : MyWebSocket(context, handshakeRequest, httpServerImpl), NotifyClientEventListener {

    override fun onMessageForClientReceived(notificationResponse: NotificationResponse) {

        runInBackground {
            val jsonAdapter = moshi.adapter(NotificationResponse::class.java)
            send(jsonAdapter.toJson(notificationResponse))
        }
    }


    init {
        val filter = IntentFilter(MESSAGE)
        mySharedMessageBroadcastReceiver.addSharedMessageListener(this)
        context.registerReceiver(mySharedMessageBroadcastReceiver, filter)
    }

    override fun onOpen() {
        super.onOpen()
        startRunner()

    }

    override fun onClose(
        code: NanoWSD.WebSocketFrame.CloseCode,
        reason: String,
        initiatedByRemote: Boolean
    ) {
        super.onClose(code, reason, initiatedByRemote)

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