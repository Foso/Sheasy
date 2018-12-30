package de.jensklingenberg.react.ui.notification

import components.Notification.ReactNotificationOptions
import de.jensklingenberg.react.ui.common.ReactPresenter

interface NotificationContract {

    interface View {
        /**
         *
         */
        fun showNotification(reactNotificationOptions: ReactNotificationOptions)
    }

    interface Presenter : ReactPresenter

}