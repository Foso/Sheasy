package de.jensklingenberg.sheasy;

import android.app.Application;
import android.content.Intent

import com.squareup.moshi.Moshi;
import de.jensklingenberg.sheasy.broReceiver.MySharedMessageBroadcastReceiver
import de.jensklingenberg.sheasy.model.Event
import de.jensklingenberg.sheasy.network.MyHttpServerImpl

/**
 * Created by jens on 9/2/18.
 */

class App : Application() {

    var moshi: Moshi = Moshi.Builder().build()

    val mySharedMessageBroadcastReceiver= MySharedMessageBroadcastReceiver()

    init {
        instance = this
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