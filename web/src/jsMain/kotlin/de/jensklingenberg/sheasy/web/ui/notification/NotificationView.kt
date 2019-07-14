package de.jensklingenberg.sheasy.web.ui.notification


import de.jensklingenberg.sheasy.web.model.NotificationOptions
import de.jensklingenberg.sheasy.web.usecase.NotificationUseCase
import react.*


interface NotificationVState : RState {

    var ignoreNotification: Boolean
    var notiOptions: NotificationOptions
    var notiTitle: String
}


class NotificationView : RComponent<RProps, NotificationVState>(), NotificationContract.View {
    private var presenter: NotificationContract.Presenter? = NotificationPresenter(this)
    val notificationUseCase = NotificationUseCase()


    override fun NotificationVState.init(props: RProps) {

        ignoreNotification = true
        notiOptions = NotificationOptions()
        notiTitle = ""
    }

    override fun componentDidMount() {

        presenter?.componentDidMount()
    }


    override fun showNotification(reactNotificationOptions: NotificationOptions) {

        setState {
            notiTitle = reactNotificationOptions.title ?: ""
            notiOptions = reactNotificationOptions
            this.ignoreNotification = false
        }

    }

    override fun componentWillUnmount() {
        presenter?.componentWillUnmount()

    }


    override fun RBuilder.render() {
        notificationUseCase.showNotification(this, state.notiOptions)
    }

}

fun RBuilder.NotificationView() = child(NotificationView::class) {}