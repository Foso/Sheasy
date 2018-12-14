package ui.notification

import components.Notification.ReactNotificationOptions
import ui.common.ReactPresenter

interface NotificationContract {

    interface View {
        /**
         *
         */
        fun showNotification(reactNotificationOptions: ReactNotificationOptions)
    }

    interface Presenter : ReactPresenter {


    }

}