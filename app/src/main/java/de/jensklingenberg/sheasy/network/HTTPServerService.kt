package de.jensklingenberg.sheasy.network

import android.app.Service
import android.content.Context
import android.content.Intent
import android.os.*
import de.jensklingenberg.sheasy.App
import de.jensklingenberg.sheasy.utils.NotificationUtils
import de.jensklingenberg.sheasy.utils.toplevel.runInBackground
import io.ktor.server.netty.NettyApplicationEngine
import java.io.IOException
import java.util.concurrent.TimeUnit
import javax.inject.Inject


/**
 * Created by jens on 25/2/18.
 */

class ServiceBinder : Binder()

class HTTPServerService : Service() {


    @Inject
    lateinit var notificationUtils1: NotificationUtils

    @Inject
    lateinit var nettyApplicationEngine: NettyApplicationEngine

    companion object {
        lateinit var bind: ServiceBinder
    }


    init {
        initializeDagger()
        bind = ServiceBinder()
    }

    private fun initializeDagger() = App.appComponent.inject(this)

    override fun onBind(p0: Intent?): IBinder = bind


    override fun onCreate() {
        super.onCreate()

        runInBackground {
            nettyApplicationEngine.start(wait = true)
            notificationUtils1.generateBundle()

        }

        try {

            val v = getSystemService(Context.VIBRATOR_SERVICE) as Vibrator
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                v.vibrate(VibrationEffect.createOneShot(500, VibrationEffect.DEFAULT_AMPLITUDE))
            } else {
                //deprecated in API 26
                v.vibrate(500)
            }

        } catch (e: IOException) {
            e.printStackTrace()
        }


    }

    override fun stopService(name: Intent?): Boolean {
        nettyApplicationEngine.stop(0L, 0L, TimeUnit.SECONDS)
        return super.stopService(name)

    }

    override fun onDestroy() {
        nettyApplicationEngine.stop(0L, 0L, TimeUnit.SECONDS)
        super.onDestroy()
    }


}

