package de.jensklingenberg.sheasy.web.usecase

import de.jensklingenberg.sheasy.web.components.Notification.Notification
import de.jensklingenberg.sheasy.web.components.Notification.ReactNotificationOptions
import react.RBuilder

data class NotificationOptions(
    val title:String?="",
    val subText:String?="",
    val icon:String?="",
    val tag:String?=""
)

class NotificationUseCase{

    fun showNotification(rBuilder: RBuilder,notificationOptions:NotificationOptions){


        val notiOptions = object : ReactNotificationOptions {
            override var tag: String? = notificationOptions.tag
            override var icon: String? = notificationOptions.icon
            override var body: String? = notificationOptions.subText
            override var title: String? = notificationOptions.title
        }

        rBuilder.run {
            Notification {
                attrs {
                    title = notificationOptions.title
                    timeout = 5000
                    options = notiOptions

                }
            }
        }

    }

}