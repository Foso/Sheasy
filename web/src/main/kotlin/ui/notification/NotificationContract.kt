package ui.notification

import components.Notification.ReactNotificationOptions
import ui.ReactPresenter

interface NotificationContract {

    interface View {

        fun showNotification(text: ReactNotificationOptions)
    }

    interface Presenter : ReactPresenter {


    }

}