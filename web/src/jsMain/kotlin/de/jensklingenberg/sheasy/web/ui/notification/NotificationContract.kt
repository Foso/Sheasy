package de.jensklingenberg.sheasy.web.ui.notification

import de.jensklingenberg.sheasy.web.components.Notification.ReactNotificationOptions
import de.jensklingenberg.sheasy.web.ui.common.ReactPresenter

interface NotificationContract {

    interface View {
        /**
         *
         */
        fun showNotification(reactNotificationOptions: ReactNotificationOptions)
    }

    interface Presenter : ReactPresenter

}