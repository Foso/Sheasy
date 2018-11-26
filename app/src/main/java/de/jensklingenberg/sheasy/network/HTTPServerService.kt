package de.jensklingenberg.sheasy.network

import android.app.Service
import android.content.Intent
import android.os.Binder
import android.os.IBinder
import androidx.fragment.app.FragmentActivity
import de.jensklingenberg.sheasy.App
import de.jensklingenberg.sheasy.utils.NotificationUtils
import de.jensklingenberg.sheasy.utils.UseCase.VibrationUseCase
import javax.inject.Inject


/**
 * Created by jens on 25/2/18.
 */

class ServiceBinder : Binder()

class HTTPServerService : Service() {


    @Inject
    lateinit var notificationUtils1: NotificationUtils

    @Inject
    lateinit var vibrationUseCase: VibrationUseCase

    @Inject
    lateinit var nettyApplicationEngine: Server

    companion object {
        lateinit var bind: ServiceBinder
        fun startIntent(activity: FragmentActivity?) = Intent(activity, HTTPServerService::class.java)
    }


    init {
        initializeDagger()
        bind = ServiceBinder()
    }

    private fun initializeDagger() = App.appComponent.inject(this)

    override fun onBind(p0: Intent?): IBinder = bind


    override fun onCreate() {
        super.onCreate()
        notificationUtils1.generateBundle()


        nettyApplicationEngine.start()


        vibrationUseCase.vibrate()
    }

    override fun stopService(name: Intent?): Boolean {
        nettyApplicationEngine.stop()
        return super.stopService(name)

    }

    override fun onDestroy() {
        nettyApplicationEngine.stop()
        super.onDestroy()
    }


}

