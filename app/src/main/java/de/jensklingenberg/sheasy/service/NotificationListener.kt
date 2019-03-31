package de.jensklingenberg.sheasy.service

import android.app.Notification
import android.service.notification.NotificationListenerService
import android.service.notification.StatusBarNotification
import android.util.Log
import de.jensklingenberg.sheasy.App
import de.jensklingenberg.sheasy.data.notification.NotificationDataSource
import de.jensklingenberg.sheasy.web.model.response.NotificationResponse
import javax.inject.Inject


/**
 * Created by jens on 16/2/18.
 */


class NotificationListener : NotificationListenerService() {

    @Inject
    lateinit var notificationDataSource: NotificationDataSource

    init {
        initializeDagger()
    }

    private fun initializeDagger() = App.appComponent.inject(this)


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

            notificationDataSource.addNotification(notResponse)

            //  val pipp = Intent(MySharedMessageBroadcastReceiver.ACTION_NOTIFICATION)
            //   pipp.putExtra(MySharedMessageBroadcastReceiver.ACTION_NOTIFICATION, notResponse)

            //  sendBroadcast(pipp)
        }

    }


}