package de.jensklingenberg.sheasy.network

import android.app.Service
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.Binder
import android.os.IBinder
import de.jensklingenberg.sheasy.App
import de.jensklingenberg.sheasy.ui.common.OnResultActivity
import de.jensklingenberg.sheasy.utils.ScreenRecord
import de.jensklingenberg.sheasy.utils.UseCase.NotificationUseCase
import de.jensklingenberg.sheasy.web.model.Device
import javax.inject.Inject


/**
 * Created by jens on 25/2/18.
 */

class ServiceBinder : Binder()

class HTTPServerService : Service(), ScreenRecord.ImageReadyListener {


    companion object {
        lateinit var bind: ServiceBinder
        fun getIntent(context: Context) =
            Intent(context, HTTPServerService::class.java)

        fun stopIntent(context: Context) =
            Intent(context, HTTPServerService::class.java).apply {
                action = "STOP"
            }

        val ACTION_ON_ACTIVITY_RESULT = "ACTION_ON_ACTIVITY_RESULT"
        val AUTHORIZE_DEVICE = "AUTHORIZE_DEVICE"
    }

    @Inject
    lateinit var notificationUtils1: NotificationUseCase

    @Inject
    lateinit var server: Server

    @Inject
    lateinit var screenRecord: ScreenRecord

    @Inject
    lateinit var sheasyPref: SheasyPrefDataSource

    /****************************************** Lifecycle methods  */


    init {
        initializeDagger()
        bind = ServiceBinder()
    }

    private fun initializeDagger() = App.appComponent.inject(this)

    override fun onBind(p0: Intent?): IBinder = bind

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {

        intent?.let {
            if (it.action?.equals("STOP")==true) {
                stopService(intent)
                return START_STICKY
            } else {
                if(intent.hasExtra(AUTHORIZE_DEVICE)){
                        val ipAddress = intent.getStringExtra(AUTHORIZE_DEVICE)
                        sheasyPref.addAuthorizedDevice(Device(ipAddress))
                }
                return super.onStartCommand(intent, flags, startId)

            }
        }

        return START_STICKY


    }


    override fun onCreate() {
        super.onCreate()
        val filter = IntentFilter()
        filter.addAction(ACTION_ON_ACTIVITY_RESULT) //further more
        registerReceiver(receiver, filter)

        notificationUtils1.showServerNotification()
        server.start()


        /* val dialogIntent = screenRecord.createScreenCaptureIntent()
         dialogIntent.component = ComponentName(baseContext, OnResultActivity::class.java)
         dialogIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
         applicationContext.startActivity(dialogIntent)*/
    }

    override fun stopService(name: Intent?): Boolean {
        server.stop()
        unregisterReceiver(receiver)

        return super.stopService(name)

    }

    override fun onDestroy() {
        unregisterReceiver(receiver)

        server.stop()
        screenRecord.stopProjection()
        super.onDestroy()
    }

    /****************************************** Listener methods  */


    override fun onImageReady(string: String) {
        server.sendData(Server.DataDestination.SCREENSHARE, string)
    }

    override fun onImageByte(byteArray: ByteArray) {
        server.sendData(Server.DataDestination.SCREENSHARE, byteArray)
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

