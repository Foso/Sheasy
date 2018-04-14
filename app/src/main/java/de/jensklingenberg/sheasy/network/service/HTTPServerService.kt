package de.jensklingenberg.sheasy.network.service

import android.app.Service
import android.content.Intent
import android.os.Binder
import android.os.IBinder
import android.util.Log
import de.jensklingenberg.sheasy.factories.ServerFactory
import de.jensklingenberg.sheasy.interfaces.MyHttpServer
import java.io.IOException

/**
 * Created by jens on 25/2/18.
 */
class HTTPServerService : Service() {
    private val mBinder = ServiceBinder()
    private var serverImpl: MyHttpServer? = null

    inner class ServiceBinder : Binder() {
        val playerService: HTTPServerService
            get() = this@HTTPServerService
    }


    override fun onBind(p0: Intent?): IBinder {
        return mBinder
    }

    override fun onCreate() {
        super.onCreate()
        try {

            serverImpl = ServerFactory.createHTTPServer(this)
            serverImpl?.start(10000)
            Log.i("TAG", "Server is started: " + serverImpl?.getHostname())


        } catch (e: IOException) {
            e.printStackTrace()
        }
    }

    override fun stopService(name: Intent?): Boolean {
        serverImpl?.stop()
        return super.stopService(name)

    }


}

