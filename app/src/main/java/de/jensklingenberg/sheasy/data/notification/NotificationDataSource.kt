package de.jensklingenberg.sheasy.data.notification

import de.jensklingenberg.sheasy.model.Notification
import io.reactivex.subjects.PublishSubject

interface NotificationDataSource {
    val notificationSubject: PublishSubject<Notification>
    fun addNotification(notResponse: Notification)
}
