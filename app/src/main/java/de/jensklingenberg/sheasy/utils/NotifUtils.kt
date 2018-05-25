package de.jensklingenberg.sheasy.utils

import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.support.v4.app.NotificationCompat
import de.jensklingenberg.sheasy.R
import de.jensklingenberg.sheasy.extension.notifcationManager

class NotifUtils {
    companion object {

        fun showConnectionRequest(context: Context, ipaddress: String) {
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
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .addAction(R.mipmap.ic_launcher, "Accept", replyPendingIntent)

                .addAction(R.mipmap.ic_launcher, "No Accept", replyPendingIntent).build()


            context.notifcationManager().notify(103, mBuilder)


        }


    }
}