package de.jensklingenberg.sheasy.data.notification

import de.jensklingenberg.sheasy.web.model.response.NotificationResponse
import io.reactivex.subjects.PublishSubject

class NotificationRepository : NotificationDataSource {


    var notifications = arrayListOf<NotificationResponse>()
   override val snackbar: PublishSubject<NotificationResponse> = PublishSubject.create()


    override fun addNotification(notResponse: NotificationResponse) {
       notifications.add(notResponse)
        snackbar.onNext(notResponse)
    }

}