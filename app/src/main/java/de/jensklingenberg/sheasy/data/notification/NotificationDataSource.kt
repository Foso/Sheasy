package de.jensklingenberg.sheasy.data.notification

import de.jensklingenberg.sheasy.web.model.response.NotificationResponse
import io.reactivex.subjects.PublishSubject

interface NotificationDataSource {
    val snackbar: PublishSubject<NotificationResponse>
    fun addNotification(notResponse: NotificationResponse)
}
