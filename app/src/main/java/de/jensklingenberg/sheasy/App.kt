package de.jensklingenberg.sheasy;

import android.app.Application;
import android.content.Intent

import com.squareup.moshi.Moshi;
import de.jensklingenberg.sheasy.model.Event
import de.jensklingenberg.sheasy.network.MyHttpServerImpl

/**
 * Created by jens on 9/2/18.
 */

class App : Application() {

    var moshi: Moshi? = null
        private set

    init {
        instance = this
    }

    override fun onCreate() {
        super.onCreate()
        setupMoshi()
    }

    private fun setupMoshi() {
        moshi = Moshi.Builder()
                .build()
    }

    companion object {
        lateinit var instance: App
        val ACTION_NLS_CONTROL = "com.seven.notificationlistenerdemo.NLSCONTROL"


    }


    fun sendBroadcast(category: String, text: String) {
        val pipp = Intent(MyHttpServerImpl.ACTION_SHARE)
        pipp.putExtra(MyHttpServerImpl.ACTION_SHARE, Event(category, text))
        App.instance.sendBroadcast(pipp)

    }


}