package de.jensklingenberg.sheasy.network

import android.annotation.SuppressLint
import android.util.Log
import com.squareup.moshi.Moshi
import de.jensklingenberg.sheasy.App
import de.jensklingenberg.sheasy.model.NotificationResponse
import de.jensklingenberg.sheasy.utils.extension.toJson
import fi.iki.elonen.NanoHTTPD
import fi.iki.elonen.NanoWSD
import io.reactivex.Observable
import io.reactivex.schedulers.Schedulers
import java.io.IOException
import java.util.concurrent.TimeUnit
import javax.inject.Inject


open class MyWebSocket(handshake: NanoHTTPD.IHTTPSession?) : NanoWSD.WebSocket(handshake) {
    var isClosed = false
    val TAG = javaClass.simpleName


    @Inject
    lateinit var moshi: Moshi

    init {
        initializeDagger()
    }

    override fun onClose(
        code: NanoWSD.WebSocketFrame.CloseCode,
        reason: String,
        initiatedByRemote: Boolean
    ) {
        isClosed = true

    }

    override fun onMessage(message: NanoWSD.WebSocketFrame) {
        Log.d(TAG, message.textPayload.toString())
        Log.d(TAG, "onMessage: ")
        message.setUnmasked()

    }

    override fun onPong(pong: NanoWSD.WebSocketFrame) {
        Log.d(TAG, "onPong: ")
    }

    override fun onException(exception: IOException) {
        Log.d(TAG, "onException: " + exception.message)
    }

    private fun initializeDagger() = App.appComponent.inject(this)

    override fun onOpen() {
        startRunner()
    }

    @SuppressLint("CheckResult")
    private fun startRunner() {

        Observable
            .fromCallable {
                val pingframe =
                    NanoWSD.WebSocketFrame(NanoWSD.WebSocketFrame.OpCode.Ping, false, "")
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
            }
            .delay(1, TimeUnit.SECONDS)
            .repeatUntil { isClosed }
            .subscribeOn(Schedulers.newThread())
            .observeOn(Schedulers.newThread())
            .subscribe { result ->
                //Use result for something
            }


    }


}
