package de.jensklingenberg.sheasy.network.websocket

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.util.Log
import com.squareup.moshi.Moshi
import de.jensklingenberg.sheasy.App
import de.jensklingenberg.sheasy.network.MyHttpServer
import de.jensklingenberg.sheasy.model.NotificationResponse
import de.jensklingenberg.sheasy.helpers.NotifLis
import de.jensklingenberg.sheasy.toplevel.runInBackground
import fi.iki.elonen.NanoHTTPD
import fi.iki.elonen.NanoWSD
import io.reactivex.Observable
import io.reactivex.schedulers.Schedulers
import java.io.IOException
import java.util.concurrent.TimeUnit

/**
 * Created by jens on 18/2/18.
 */
class NotificationWebsocket(context: Context, handshakeRequest: NanoHTTPD.IHTTPSession, httpServer: MyHttpServer) : MyWebSocket(context, handshakeRequest, httpServer), NotifLis {


    init {
        val filter = IntentFilter("com.seven.notificationlistenerdemo.NLSCONTROL")
        // com.seven.notificationlistenerdemo.NLSCONTROL
        val tt = MyBroadcastReceiver(this)
        context.registerReceiver(tt, filter);

    }


    override fun onNotification(test: NotificationResponse) {
        Log.d("HIER", test.packageName)

        runInBackground {
            val jsonAdapter = Moshi.Builder()
                    .build().adapter(NotificationResponse::class.java)


            send(jsonAdapter.toJson(test))
        }

    }


    override fun send(payload: String?) {
        super.send(payload)
    }

    fun add(test: NotificationResponse) {
        send(test.packageName.toString())
    }

    override fun onOpen() {
        super.onOpen()
        startRunner()

    }

    private fun startRunner() {

        Observable.fromCallable {

            Log.d(TAG, "onOpen: 2")

            val pingframe = NanoWSD.WebSocketFrame(NanoWSD.WebSocketFrame.OpCode.Ping, false, "")

            ping(pingframe.binaryPayload)

            val jsonAdapter = Moshi.Builder().build().adapter(NotificationResponse::class.java)

            send(jsonAdapter.toJson(NotificationResponse("test.package", "Testnotification", "testtext", "testsubtext", 0L)))

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
        class MyBroadcastReceiver(val notifLis: NotifLis) : BroadcastReceiver() {

            override fun onReceive(context: Context, intent: Intent) {
                val test: NotificationResponse = intent.extras.getParcelable(App.ACTION_NLS_CONTROL)

                try {
                    notifLis.onNotification(test)
                } catch (e: IOException) {
                    e.printStackTrace()
                }

            }


        }
    }


}