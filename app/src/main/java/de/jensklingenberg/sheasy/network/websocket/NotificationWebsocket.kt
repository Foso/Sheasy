package de.jensklingenberg.sheasy.network.websocket

import android.content.Context
import android.content.IntentFilter
import android.util.Log
import com.squareup.moshi.Moshi
import de.jensklingenberg.sheasy.broReceiver.MySharedMessageBroadcastReceiver
import de.jensklingenberg.sheasy.interfaces.OnNotificationReceivedListener
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
class NotificationWebsocket(
    context: Context,
    handshakeRequest: NanoHTTPD.IHTTPSession,
    httpServerImpl: MyHttpServerImpl,
    mySharedMessageBroadcastReceiver: MySharedMessageBroadcastReceiver,
    val moshi: Moshi
) : MyWebSocket(context, handshakeRequest, httpServerImpl), OnNotificationReceivedListener {


    init {
        val filter = IntentFilter(MySharedMessageBroadcastReceiver.ACTION_NOTIFICATION)
        val tt = mySharedMessageBroadcastReceiver
        context.registerReceiver(tt, filter);

    }


    override fun onNotificationReceived(notificationResponse: NotificationResponse) {
        Log.d("HIER", notificationResponse.packageName)

        runInBackground {
            val jsonAdapter = moshi.adapter(NotificationResponse::class.java)

            send(jsonAdapter.toJson(notificationResponse))
        }

    }


    override fun send(payload: String?) {
        super.send(payload)
    }

    fun add(test: NotificationResponse) {
        send(test.packageName)
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

            Log.d(TAG, "onOpen: 2")

            val pingframe = NanoWSD.WebSocketFrame(NanoWSD.WebSocketFrame.OpCode.Ping, false, "")

            ping(pingframe.binaryPayload)

            val jsonAdapter = Moshi.Builder().build().adapter(NotificationResponse::class.java)

            send(
                jsonAdapter.toJson(
                    NotificationResponse(
                        "test.package",
                        "Testnotification",
                        "testtext",
                        "testsubtext",
                        0L
                    )
                )
            )

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