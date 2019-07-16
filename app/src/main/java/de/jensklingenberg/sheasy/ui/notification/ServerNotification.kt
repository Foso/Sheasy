package de.jensklingenberg.sheasy.ui.notification

import android.app.PendingIntent
import android.content.Context
import android.widget.RemoteViews
import de.jensklingenberg.sheasy.R
import de.jensklingenberg.sheasy.service.HTTPServerService

class ServerNotification(val context: Context, val notificationItem: NotificationItem) :
    RemoteViews(context.packageName, R.layout.notification_server_control),
    ServerNotificationContract.View {

    val presenter: ServerNotificationContract.Presenter = ServerNotificationPresenter(this)

    init {
        presenter.onCreate()
    }


    override fun setData() {
        //Next create a pending intent and make it stop our video playback.
        val intent = PendingIntent.getService(context, 0, HTTPServerService.stopIntent(this.context), 0)
        setOnClickPendingIntent(R.id.stopBtn, intent)
        setTextViewText(R.id.txtvTitle, notificationItem.title)
    }
}

data class NotificationItem(val title: String = "", val subtitle: String = "")