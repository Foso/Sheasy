package de.jensklingenberg.sheasy.service

import android.app.Notification
import android.service.notification.NotificationListenerService
import android.service.notification.StatusBarNotification
import android.util.Log
import de.jensklingenberg.sheasy.App
import de.jensklingenberg.sheasy.data.notification.NotificationDataSource
import java.util.*
import javax.inject.Inject


/**
 * NotificationListenerService to read the notifications on the device
 */


class NotificationListener : NotificationListenerService() {




    @Inject
    lateinit var notificationDataSource: NotificationDataSource

    init {
        initializeDagger()
    }

    private fun initializeDagger() = App.appComponent.inject(this)


    override fun onNotificationPosted(sbn: StatusBarNotification?) {
        super.onNotificationPosted(sbn)
        sbn?.notification?.extras?.let {
            val title = it.getString(Notification.EXTRA_TITLE) ?: ""
            val text = it.getString(Notification.EXTRA_TEXT) ?: ""
            val subText = it.getString(Notification.EXTRA_SUB_TEXT) ?: ""

            val postTime = Date().toString()
            val packageName = sbn.packageName ?: ""
            Log.d("THIS", title)
            val notification = de.jensklingenberg.sheasy.model.Notification(packageName, title, text, subText, postTime)

            notificationDataSource.addNotification(notification)

        }

    }


}