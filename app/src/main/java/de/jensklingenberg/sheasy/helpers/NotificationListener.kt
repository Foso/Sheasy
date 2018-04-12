package de.jensklingenberg.sheasy.helpers

import android.app.Notification
import android.content.Intent
import android.service.notification.NotificationListenerService
import android.service.notification.StatusBarNotification
import android.util.Log
import de.jensklingenberg.sheasy.App
import de.jensklingenberg.sheasy.model.NotificationResponse


/**
 * Created by jens on 16/2/18.
 */


class NotificationListener : NotificationListenerService() {


    companion object {
        var notifi = arrayListOf<NotificationResponse>()


    }


    override fun onNotificationPosted(sbn: StatusBarNotification?) {
        super.onNotificationPosted(sbn)
        val title = sbn?.notification?.extras?.getString(Notification.EXTRA_TITLE) ?: ""
        val text = sbn?.notification?.extras?.getString(Notification.EXTRA_TEXT) ?: ""
        val subText = sbn?.notification?.extras?.getString(Notification.EXTRA_SUB_TEXT) ?: ""

        val postTime = sbn?.postTime ?: 0
        val packageName = sbn?.packageName ?: ""
        Log.d("THIS", title)
        val notResponse = NotificationResponse(packageName, title, text, subText, postTime)
        notifi.add(notResponse)
        val pipp = Intent(App.ACTION_NLS_CONTROL)
        pipp.putExtra(App.ACTION_NLS_CONTROL, notResponse)

        sendBroadcast(pipp)

    }

    override fun onNotificationRemoved(sbn: StatusBarNotification?) {
        super.onNotificationRemoved(sbn)
    }


}