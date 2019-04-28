package de.jensklingenberg.sheasy.data.notification

import de.jensklingenberg.sheasy.model.Notification
import io.reactivex.subjects.PublishSubject

class NotificationRepository : NotificationDataSource {


    var notifications = arrayListOf<Notification>()
    override val notificationSubject: PublishSubject<Notification> = PublishSubject.create()


    override fun addNotification(notResponse: Notification) {
        notifications.add(notResponse)
        notificationSubject.onNext(notResponse)
    }

}