package de.jensklingenberg.sheasy.helpers;

import de.jensklingenberg.sheasy.model.NotificationResponse


interface NotifLis {
    fun onNotification(test: NotificationResponse)
}

interface NotifyClientEvent {
    fun onMessage(test: NotificationResponse)
}