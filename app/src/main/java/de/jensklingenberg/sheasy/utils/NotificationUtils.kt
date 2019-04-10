package de.jensklingenberg.sheasy.utils

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.graphics.BitmapFactory
import android.graphics.Color
import android.media.RingtoneManager
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.core.app.NotificationCompat
import de.jensklingenberg.sheasy.App
import de.jensklingenberg.sheasy.BuildConfig
import de.jensklingenberg.sheasy.R
import de.jensklingenberg.sheasy.network.HTTPServerService
import de.jensklingenberg.sheasy.network.HTTPServerService.Companion.AUTHORIZE_DEVICE
import de.jensklingenberg.sheasy.utils.UseCase.NotificationUseCase
import java.util.*
import javax.inject.Inject


class NotificationUtils : NotificationUseCase {


    @Inject
    lateinit var context: Context

    @Inject
    lateinit var notificationManager: NotificationManager


    init {
        initializeDagger()
    }

    private fun initializeDagger() = App.appComponent.inject(this)

    @RequiresApi(Build.VERSION_CODES.O)
    private fun createNotificationChannel(channelId: String, nameResId: Int): NotificationChannel {
        val channelName = context.getString(nameResId)
        val notificationChannel = NotificationChannel(
            channelId, channelName, android.app.NotificationManager.IMPORTANCE_HIGH
        )
        notificationChannel.apply {
            enableLights(true)
            enableVibration(true)
            setBypassDnd(true)
            setShowBadge(true)
            importance = android.app.NotificationManager.IMPORTANCE_HIGH
        }

        return notificationChannel
    }


    override fun showConnectionRequest(ipaddress: String) {


        val intent = HTTPServerService.getIntent(context).apply {
            putExtra(AUTHORIZE_DEVICE, ipaddress)
        }

        val replyPendingIntent = PendingIntent.getService(
            context, 0 /* Request code */, intent,
            PendingIntent.FLAG_CANCEL_CURRENT
        )

        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
        createChannel()
        val pendingIntent = PendingIntent.getActivity(
            context, 0 /* Request code */, intent,
            PendingIntent.FLAG_ONE_SHOT
        )
        val defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION)

        val notificationBuilder = NotificationCompat.Builder(context, "channelID")
            .setSmallIcon(R.mipmap.ic_launcher_round)
            .setContentTitle("Connection from " + ipaddress)
            .setContentText("Do you want to accept the connection")
            .setAutoCancel(true)
            .setSound(defaultSoundUri)
            .setContentIntent(pendingIntent)
            .addAction(R.mipmap.ic_launcher, "Accept", replyPendingIntent)

            .addAction(R.mipmap.ic_launcher, "No Accept", replyPendingIntent)

        val notificationId = 1
        notificationManager?.notify(notificationId, notificationBuilder.build())



    }


    fun showNotification(heading: String, description: String, imageUrl: String, intent: Intent) {
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
        createChannel()
        val pendingIntent = PendingIntent.getActivity(
            context, 0 /* Request code */, intent,
            PendingIntent.FLAG_ONE_SHOT
        )
        val defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION)

        val notificationBuilder = NotificationCompat.Builder(context, "channelID")
            .setSmallIcon(R.mipmap.ic_launcher)
            .setContentTitle(heading)
            .setPriority(NotificationCompat.PRIORITY_MAX)

            .setContentText(description)
            .setAutoCancel(true)
            .setSound(defaultSoundUri)
            .setContentIntent(pendingIntent)

        val notificationId = 1
        notificationManager?.notify(notificationId, notificationBuilder.build())
    }

    fun createChannel() {
        if (Build.VERSION.SDK_INT < 26) {
            return
        }
        val channel = NotificationChannel("channelID", "name", NotificationManager.IMPORTANCE_DEFAULT)
        channel.description = "Description"
        notificationManager.createNotificationChannel(channel)
    }


    override fun showServerNotification() {

        showNotification("Sheasy Server running","Server running at " + NetworkUtils.getIP(context) + ":" + BuildConfig.SERVER_PORT,"hhhhh",Intent())


    }


    companion object {
        val NOTIFICATION_GROUP_KEY = "group_key"
        private var BIG_TEXT_NOTIFICATION_KEY = 0
        val PRIMARY_CHANNEL = "default"
        val SECONDARY_CHANNEL = "second"
    }


}