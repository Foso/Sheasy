package de.jensklingenberg.sheasy.service

import android.app.Notification
import android.content.Intent
import android.service.notification.NotificationListenerService
import android.service.notification.StatusBarNotification
import android.util.Log
import de.jensklingenberg.sheasy.broReceiver.MySharedMessageBroadcastReceiver
import de.jensklingenberg.sheasy.model.NotificationResponse


/**
 * Created by jens on 16/2/18.
 */


class NotificationListener : NotificationListenerService() {


    companion object {
        var notifi = arrayListOf<NotificationResponse>()


    }

    override fun onNotificationRemoved(sbn: StatusBarNotification?) {
        super.onNotificationRemoved(sbn)
    }


    override fun onNotificationPosted(sbn: StatusBarNotification?) {
        super.onNotificationPosted(sbn)
        sbn?.notification?.extras?.let {
            val title = it.getString(Notification.EXTRA_TITLE) ?: ""
            val text = it.getString(Notification.EXTRA_TEXT) ?: ""
            val subText = it.getString(Notification.EXTRA_SUB_TEXT) ?: ""

            val postTime = sbn.postTime
            val packageName = sbn.packageName ?: ""
            Log.d("THIS", title)
            val notResponse = NotificationResponse(packageName, title, text, subText, postTime)
            notifi.add(notResponse)
            val pipp = Intent(MySharedMessageBroadcastReceiver.ACTION_NOTIFICATION)
            pipp.putExtra(MySharedMessageBroadcastReceiver.ACTION_NOTIFICATION, notResponse)

            sendBroadcast(pipp)
        }

    }


}