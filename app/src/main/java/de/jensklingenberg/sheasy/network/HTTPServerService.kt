package de.jensklingenberg.sheasy.network

import android.app.Service
import android.content.*
import android.os.Binder
import android.os.IBinder
import androidx.fragment.app.FragmentActivity
import de.jensklingenberg.sheasy.App
import de.jensklingenberg.sheasy.utils.NotificationUtils
import de.jensklingenberg.sheasy.utils.ScreenRecord
import de.jensklingenberg.sheasy.utils.UseCase.VibrationUseCase
import javax.inject.Inject


/**
 * Created by jens on 25/2/18.
 */

class ServiceBinder : Binder()

class HTTPServerService : Service(), ScreenRecord.ImageReadyListener {



    companion object {
        lateinit var bind: ServiceBinder
        fun startIntent(activity: FragmentActivity?) =
            Intent(activity, HTTPServerService::class.java)

        val ACTION_ON_ACTIVITY_RESULT = "ACTION_ON_ACTIVITY_RESULT"
    }

    @Inject
    lateinit var notificationUtils1: NotificationUtils

    @Inject
    lateinit var vibrationUseCase: VibrationUseCase

    @Inject
    lateinit var server: Server

    @Inject
    lateinit var screenRecord: ScreenRecord


    init {
        initializeDagger()
        bind = ServiceBinder()
    }

    private fun initializeDagger() = App.appComponent.inject(this)

    override fun onBind(p0: Intent?): IBinder = bind


    override fun onImageReady(string: String) {
        server.sendData(Server.DataDestination.SCREENSHARE, string)
    }

    override fun onImageByte(byteArray: ByteArray) {
        server.sendData(Server.DataDestination.SCREENSHARE, byteArray)
    }

    override fun onCreate() {
        super.onCreate()
        val filter = IntentFilter()
        filter.addAction(ACTION_ON_ACTIVITY_RESULT) //further more
        registerReceiver(receiver, filter)

        notificationUtils1.generateBundle()
        server.start()


        vibrationUseCase.vibrate()

        val dialogIntent = screenRecord.createScreenCaptureIntent()
        dialogIntent.component = ComponentName(baseContext, OnResultActivity::class.java)
        dialogIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        applicationContext.startActivity(dialogIntent)
    }

    override fun stopService(name: Intent?): Boolean {
        server.stop()
        return super.stopService(name)

    }

    override fun onDestroy() {
        server.stop()
        screenRecord.stopProjection()
        super.onDestroy()
    }


    private val receiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context, intent: Intent) {
            val action = intent.action
            if (action == ACTION_ON_ACTIVITY_RESULT) {
                val resultCode = intent.extras.getInt(OnResultActivity.RESULT_CODE)
                screenRecord.imageReadyListener = this@HTTPServerService
                screenRecord.startRecord(resultCode, intent)

                //action for sms received
            }
        }
    }

}

