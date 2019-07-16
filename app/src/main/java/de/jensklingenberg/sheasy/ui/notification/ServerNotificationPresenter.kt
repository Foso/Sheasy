package de.jensklingenberg.sheasy.ui.notification

class ServerNotificationPresenter(val view: ServerNotificationContract.View) :
    ServerNotificationContract.Presenter {

    override fun onCreate() {
        view.setData()
    }

}