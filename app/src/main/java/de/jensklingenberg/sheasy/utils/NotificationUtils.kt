package de.jensklingenberg.sheasy.utils

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.graphics.BitmapFactory
import android.graphics.Color
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.core.app.NotificationCompat
import de.jensklingenberg.sheasy.App
import de.jensklingenberg.sheasy.BuildConfig
import de.jensklingenberg.sheasy.R
import de.jensklingenberg.sheasy.network.HTTPServerService
import de.jensklingenberg.sheasy.network.HTTPServerService.Companion.AUTHORIZE_DEVICE
import de.jensklingenberg.sheasy.utils.UseCase.NotificationUseCase
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
            context,
            System.currentTimeMillis().toInt(),
            intent, 0
        )


        val mBuilder = NotificationCompat.Builder(context, "55")
            .setSmallIcon(R.mipmap.ic_launcher)
            .setContentTitle("Connection from " + ipaddress)
            .setContentText("Do you want to accept the connection")
            .setPriority(NotificationCompat.PRIORITY_MAX)
            .setCategory(NotificationCompat.CATEGORY_ALARM)
            .setVisibility(NotificationCompat.VISIBILITY_PUBLIC)
            .setOngoing(true)
            .setLights(Color.WHITE, 1000, 1000)
            .addAction(R.mipmap.ic_launcher, "Accept", replyPendingIntent)

            .addAction(R.mipmap.ic_launcher, "No Accept", replyPendingIntent).build()


        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            notificationManager.createNotificationChannel(createNotificationChannel("1", R.id.icon))

        }
        notificationManager.notify(103, mBuilder)


    }


    override fun showServerNotification() {
        BIG_TEXT_NOTIFICATION_KEY++

        val pIntent = PendingIntent.getService(
            context,
            System.currentTimeMillis().toInt(),
            HTTPServerService.stopIntent(context), 0
        )

        // Add to your action, enabling Direct Reply for it
        val replayAction =
            NotificationCompat.Action.Builder(R.drawable.ic_launcher_background, "Replay", pIntent)
                .build()

        val noti1 = NotificationCompat.Builder(context)
            .setContentTitle("Sheasy Server running")
            .setContentText("Server running at " + NetworkUtils.getIP(context) + ":" + BuildConfig.SERVER_PORT)

            .setSubText("Server running at " + NetworkUtils.getIP(context) + ":" + BuildConfig.SERVER_PORT)
            .setAutoCancel(false)
            .setLargeIcon(
                BitmapFactory.decodeResource(
                    context.resources,
                    R.mipmap.ic_launcher
                )
            )
            .setSmallIcon(R.mipmap.ic_launcher)
            .addAction(R.mipmap.ic_launcher, "Stop Server", pIntent)

            .setContentIntent(pIntent)
            //.addAction(replayAction)
            .setGroup(NOTIFICATION_GROUP_KEY)

        notificationManager.notify(BIG_TEXT_NOTIFICATION_KEY, noti1.build())
    }


    companion object {
        val NOTIFICATION_GROUP_KEY = "group_key"
        private var BIG_TEXT_NOTIFICATION_KEY = 0
    }


}