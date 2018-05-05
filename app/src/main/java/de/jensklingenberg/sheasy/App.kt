package de.jensklingenberg.sheasy;

import android.app.Application
import android.content.Intent
import com.jakewharton.threetenabp.AndroidThreeTen
import com.squareup.moshi.Moshi
import de.jensklingenberg.sheasy.broReceiver.MySharedMessageBroadcastReceiver
import de.jensklingenberg.sheasy.model.Event
import io.ktor.application.install
import org.threeten.bp.Duration

/**
 * Created by jens on 9/2/18.
 */

class App : Application() {

    var moshi: Moshi = Moshi.Builder().build()

    val mySharedMessageBroadcastReceiver = MySharedMessageBroadcastReceiver()


    init {
        instance = this
        AndroidThreeTen.init(this);

    }


    fun io.ktor.application.Application.main() {
        install(BackportWebSocket) {
            pingPeriod = Duration.ofSeconds(60) // Disabled (null) by default
            timeout = Duration.ofSeconds(15)
            maxFrameSize =
                    Long.MAX_VALUE // Disabled (max value). The connection will be closed if surpassed this length.
            masking = false
        }

    }

    companion object {
        lateinit var instance: App

    }

    override fun onCreate() {
        super.onCreate()
    }


    fun sendBroadcast(category: String, text: String) {
        val pipp = Intent(MySharedMessageBroadcastReceiver.ACTION_SHARE).apply {
            putExtra(MySharedMessageBroadcastReceiver.ACTION_SHARE, Event(category, text))
        }
        sendBroadcast(pipp)

    }


}