package de.jensklingenberg.sheasy.web.ui.notification

import de.jensklingenberg.sheasy.web.model.NotificationOptions
import de.jensklingenberg.sheasy.web.ui.common.ReactPresenter

interface NotificationContract {

    interface View {
        fun showNotification(reactNotificationOptions: NotificationOptions)
    }

    interface Presenter : ReactPresenter

}