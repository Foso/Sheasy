package de.jensklingenberg.sheasy.data.notification

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.graphics.Color
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.core.app.NotificationCompat
import de.jensklingenberg.sheasy.App
import de.jensklingenberg.sheasy.R
import de.jensklingenberg.sheasy.data.usecase.NotificationUseCase
import de.jensklingenberg.sheasy.network.SheasyPrefDataSource
import de.jensklingenberg.sheasy.service.HTTPServerService
import de.jensklingenberg.sheasy.ui.notification.NotificationItem
import de.jensklingenberg.sheasy.ui.notification.ServerNotification
import de.jensklingenberg.sheasy.utils.NetworkUtils
import javax.inject.Inject


class NotificationProvider : NotificationUseCase {


    @Inject
    lateinit var context: Context

    @Inject
    lateinit var notificationManager: NotificationManager

    @Inject
    lateinit var sheasyPrefDataSource: SheasyPrefDataSource

    val FTP_ID = 5


    init {
        initializeDagger()
    }

    private fun initializeDagger() = App.appComponent.inject(this)

    companion object {
        const val NOTIFICATION_CHANNEL_ID_CONNECTION_REQUEST_ID = 1
        const val NOTIFICATION_CHANNEL_ID_CONNECTION_REQUEST_NAME = "Sheasy Server Notifications"

        const val NOTIFICATION_CHANNEL_ID_SERVER_STATE_ID = 2

        val NOTIFICATION_CHANNEL_CONNECTION_REQUEST_ID = "ConnectionRequestChannel"
        val NOTIFICATION_CHANNEL_SERVER_STATE_NAME = "Notification when server is running"
        val NOTIFICATION_CHANNEL_SERVER_STATE_ID = "de.jensklingenberg.sheasy.server.running"
        val CHANNEL_FTP_ID = "ftpChannel"

    }

    override fun showConnectionRequest(ipaddress: String) {


        val intent = HTTPServerService.authorizeDeviceIntent(context, ipaddress)

        val replyPendingIntent = PendingIntent.getService(
            context, 0 /* Request code */, intent,
            PendingIntent.FLAG_CANCEL_CURRENT
        )

        // intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
        createChannel(NOTIFICATION_CHANNEL_CONNECTION_REQUEST_ID)

        // val defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager)

        val notificationBuilder = NotificationCompat.Builder(context, NOTIFICATION_CHANNEL_CONNECTION_REQUEST_ID)
            .setSmallIcon(R.mipmap.ic_launcher_round)
            .setContentTitle("Connection from $ipaddress")
            .setContentText("Do you want to accept the connection")
            .setAutoCancel(true)
            //.setSound(defaultSoundUri)
            // .setContentIntent(pendingIntent)
            .addAction(R.mipmap.ic_launcher, "Accept", replyPendingIntent)

            .addAction(R.mipmap.ic_launcher, "No Accept", replyPendingIntent)

        notificationManager.notify(NOTIFICATION_CHANNEL_ID_CONNECTION_REQUEST_ID, notificationBuilder.build())


    }


    fun showNotification(notificationItem: NotificationItem, imageUrl: String, pendingIntent: PendingIntent) {

        // intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
        //   createChannel(CHANNEL_FTP_ID)
/*
        val defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION)

        val notificationBuilder = NotificationCompat.Builder(context, CHANNEL_FTP_ID)
            .setSmallIcon(R.mipmap.ic_launcher)
            .setContentTitle(notificationItem.title)
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .setContentText(notificationItem.subtitle)
            .setAutoCancel(true)
            .setCategory(Notification.CATEGORY_SERVICE)
            .setVisibility(NotificationCompat.VISIBILITY_PUBLIC)
            .setSound(defaultSoundUri)
            .setContentIntent(pendingIntent)
            .addAction(R.mipmap.ic_launcher, "Accept", pendingIntent)

        notificationManager.notify(FTP_ID, notificationBuilder.build())

        */
    }

    fun createChannel(channelID: String) {
        if (Build.VERSION.SDK_INT < 26) {
            return
        }
        val channel = NotificationChannel(
            channelID,
            NOTIFICATION_CHANNEL_ID_CONNECTION_REQUEST_NAME,
            NotificationManager.IMPORTANCE_HIGH
        )
        channel.description = "Description"
        notificationManager.createNotificationChannel(channel)
    }


    fun showServerNotification() {

        val pendingIntent = PendingIntent.getActivity(
            context, 0 /* Request code */, HTTPServerService.stopIntent(context),
            PendingIntent.FLAG_ONE_SHOT
        )



        showNotification(
            NotificationItem(
                "Sheasy Server running", "Server running at " + NetworkUtils.getIP(
                    context
                ) + ":" + sheasyPrefDataSource.httpPort
            ), "", pendingIntent
        )


    }


    override fun cancelAll() {
        notificationManager.cancelAll()
    }


    @RequiresApi(Build.VERSION_CODES.O)
    fun getServerStateNotificationChannel(): NotificationChannel {
        val chan = NotificationChannel(
            NOTIFICATION_CHANNEL_SERVER_STATE_ID,
            NOTIFICATION_CHANNEL_SERVER_STATE_NAME,
            NotificationManager.IMPORTANCE_NONE
        )
        chan.lightColor = Color.BLUE
        chan.lockscreenVisibility = Notification.VISIBILITY_PRIVATE
        return chan
    }

    override fun getForeGroundServiceNotification(context: Context): Notification {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            notificationManager.createNotificationChannel(getServerStateNotificationChannel())
        }

        val notificationBuilder = NotificationCompat.Builder(context, NOTIFICATION_CHANNEL_SERVER_STATE_ID)
        val notification = notificationBuilder.setOngoing(true)
            .setSmallIcon(R.mipmap.ic_launcher_round)
            .setContent(
                ServerNotification(
                    context, NotificationItem(
                        "Sheasy - Server running at " + NetworkUtils.getIP(
                            context
                        ) + ":" + sheasyPrefDataSource.httpPort
                    )
                )
            )
            .setChannelId(NOTIFICATION_CHANNEL_SERVER_STATE_ID)
            .setPriority(NotificationManager.IMPORTANCE_MIN)
            .setCategory(Notification.CATEGORY_SERVICE)
            .build()
        return notification
    }

}