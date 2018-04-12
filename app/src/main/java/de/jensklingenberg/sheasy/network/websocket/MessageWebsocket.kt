package de.jensklingenberg.sheasy.network.websocket

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import com.squareup.moshi.Moshi
import de.jensklingenberg.sheasy.model.NotificationResponse
import de.jensklingenberg.sheasy.helpers.NotifyClientEvent
import de.jensklingenberg.sheasy.toplevel.runInBackground
import de.jensklingenberg.sheasy.network.MyHttpServer
import fi.iki.elonen.NanoHTTPD
import fi.iki.elonen.NanoWSD
import io.reactivex.Observable
import io.reactivex.schedulers.Schedulers
import java.io.IOException
import java.util.concurrent.TimeUnit

/**
 * Created by jens on 18/2/18.
 */
class MessageWebsocket(context: Context, handshakeRequest: NanoHTTPD.IHTTPSession, httpServer: MyHttpServer) : MyWebSocket(context, handshakeRequest, httpServer), NotifyClientEvent {


    override fun onMessage(notificationResponse: NotificationResponse) {

        runInBackground {
            val jsonAdapter = Moshi.Builder()
                    .build().adapter(NotificationResponse::class.java)
            send(jsonAdapter.toJson(notificationResponse))
        }
    }


    init {
        val filter = IntentFilter(MESSAGE)
        val tt = MyBroadcastReceiver(this)
        context.registerReceiver(tt, filter)
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
                .repeat()
                .subscribeOn(Schedulers.newThread())
                .observeOn(Schedulers.newThread())
                .subscribe { result ->
                    //Use result for something
                }


    }


    companion object {
        const val MESSAGE = "MESSAGE.ACTION"

        class MyBroadcastReceiver(private val notifyClientEvent: NotifyClientEvent) : BroadcastReceiver() {

            override fun onReceive(context: Context, intent: Intent) {
                val test: NotificationResponse = intent.extras.getParcelable(MESSAGE)

                try {
                    notifyClientEvent.onMessage(test)

                } catch (e: IOException) {
                    e.printStackTrace()
                }

            }


        }
    }


}