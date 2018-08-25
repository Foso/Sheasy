package de.jensklingenberg.sheasy.network.websocket

import android.content.Context
import android.content.IntentFilter
import android.util.Log
import com.squareup.moshi.Moshi
import de.jensklingenberg.sheasy.App
import de.jensklingenberg.sheasy.broReceiver.MySharedMessageBroadcastReceiver
import de.jensklingenberg.sheasy.interfaces.OnNotificationReceivedListener
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
class NotificationWebsocket(
    context: Context,
    handshakeRequest: NanoHTTPD.IHTTPSession,
    httpServerImpl: MyHttpServerImpl
) : MyWebSocket(handshakeRequest, httpServerImpl), OnNotificationReceivedListener {

    @Inject
    lateinit var moshi: Moshi

    @Inject
    lateinit var msbr: MySharedMessageBroadcastReceiver

    init {
        initializeDagger()
    }

    private fun initializeDagger() = App.appComponent.inject(this)

    init {
        val filter = IntentFilter(MySharedMessageBroadcastReceiver.ACTION_NOTIFICATION)
        context.registerReceiver(msbr, filter);

    }


    override fun onNotificationReceived(notificationResponse: NotificationResponse) {
        Log.d("HIER", notificationResponse.packageName)

        runInBackground {
            send(moshi.toJson(notificationResponse))
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

            send(
                moshi.toJson(
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