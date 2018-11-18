package de.jensklingenberg.sheasy.utils

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.graphics.BitmapFactory
import android.graphics.Color
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.core.app.NotificationCompat
import de.jensklingenberg.sheasy.App
import de.jensklingenberg.sheasy.BuildConfig
import de.jensklingenberg.sheasy.R
import de.jensklingenberg.sheasy.ui.MainActivity
import javax.inject.Inject


class NotificationUtils {


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
        notificationChannel.enableLights(true)
        notificationChannel.enableVibration(true)
        notificationChannel.setBypassDnd(true)
        notificationChannel.setShowBadge(true)
        notificationChannel.importance = android.app.NotificationManager.IMPORTANCE_HIGH
        return notificationChannel
    }


    fun showConnectionRequest(ipaddress: String) {
        val replyPendingIntent = PendingIntent.getBroadcast(
            context,
            1,
            Intent(),
            PendingIntent.FLAG_UPDATE_CURRENT
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

    fun generateBundle() {

        generateSingleNotification(context)


        //create summary notification
        setSummaryNotification()
    }

    private fun setSummaryNotification() {
        val summaryNotification = NotificationCompat.Builder(context)
            .setContentText("Server running")
            .setAutoCancel(false)
            .setLargeIcon(
                BitmapFactory.decodeResource(
                    context.resources,
                    R.mipmap.ic_launcher
                )
            )
            .setSmallIcon(R.mipmap.ic_launcher)
            .setStyle(
                NotificationCompat.InboxStyle()
                    .setBigContentTitle("Bundled notification tips")
                    .addLine("Tip 1")
                    .addLine("Tip 2")
                    .addLine("Tip 3")
                    .addLine("Tip 4")
                    .setSummaryText("Total 4 tips")
            )
            .setGroup(NOTIFICATION_GROUP_KEY)
            .setGroupSummary(true)

        notificationManager.notify(100, summaryNotification.build())
    }

    private fun generateSingleNotification(context: Context) {
        BIG_TEXT_NOTIFICATION_KEY++

        val pIntent = PendingIntent.getActivity(
            context,
            System.currentTimeMillis().toInt(),
            Intent(context, MainActivity::class.java), 0
        )

        // Add to your action, enabling Direct Reply for it
        val replayAction =
            NotificationCompat.Action.Builder(R.drawable.ic_launcher_background, "Replay", pIntent)
                .build()

        val noti1 = NotificationCompat.Builder(context)
            .setContentText("Sheasy Server running")
            .setAutoCancel(false)
            .setLargeIcon(
                BitmapFactory.decodeResource(
                    context.resources,
                    R.mipmap.ic_launcher
                )
            )
            .setSmallIcon(R.mipmap.ic_launcher)
            .setStyle(
                NotificationCompat.BigTextStyle()
                    .bigText("Server running at " + NetworkUtils.getIP(context) + ":" + BuildConfig.SERVER_PORT)
                    .setBigContentTitle("Sheasy: ${BIG_TEXT_NOTIFICATION_KEY}")
                    .setSummaryText("Tip to build notification")
            )
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