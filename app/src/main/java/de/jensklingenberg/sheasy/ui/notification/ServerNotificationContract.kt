package de.jensklingenberg.sheasy.ui.notification

interface ServerNotificationContract {
    interface View {
        fun setData()
    }

    interface Presenter {
        fun onCreate()
    }
}

