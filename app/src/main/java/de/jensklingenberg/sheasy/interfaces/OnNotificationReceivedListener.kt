package de.jensklingenberg.sheasy.interfaces

import de.jensklingenberg.sheasy.model.NotificationResponse


interface OnNotificationReceivedListener {
    fun onNotificationReceived(notificationResponse: NotificationResponse)
}

interface NotifyClientEventListener {
    fun onMessageForClientReceived(notificationResponse: NotificationResponse)
}

interface OnScreenShareEventListener {
    fun onDataForClientReceived(notificationResponse: String)
}