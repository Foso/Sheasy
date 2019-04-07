package de.jensklingenberg.sheasy.web.ui.notification

import de.jensklingenberg.sheasy.web.components.Notification.ReactNotificationOptions
import de.jensklingenberg.sheasy.web.network.MyWebSocket
import de.jensklingenberg.sheasy.web.ui.common.ReactPresenter
import de.jensklingenberg.sheasy.web.usecase.NotificationOptions

interface NotificationContract {

    interface View {
        /**
         *
         */
        fun showNotification(reactNotificationOptions: NotificationOptions)
    }

    interface Presenter : ReactPresenter, MyWebSocket.WebSocketListener{

    }

}