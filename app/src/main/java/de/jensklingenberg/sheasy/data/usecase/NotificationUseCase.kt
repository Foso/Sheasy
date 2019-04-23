package de.jensklingenberg.sheasy.data.usecase

import android.app.Notification
import android.content.Context

interface NotificationUseCase {
    fun showConnectionRequest(ipaddress: String)
    fun showServerNotification()
    fun cancelAll()
    fun getForeGroundServiceNotification(context: Context):Notification
}