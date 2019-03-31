package de.jensklingenberg.sheasy.web.ui.notification


import components.materialui.Button
import de.jensklingenberg.sheasy.web.components.Notification.Notification
import de.jensklingenberg.sheasy.web.components.Notification.ReactNotificationOptions
import de.jensklingenberg.sheasy.web.components.Notification.defaultReactNotificationOptions
import de.jensklingenberg.sheasy.web.components.reactstrap.Tooltip
import de.jensklingenberg.sheasy.web.usecase.NotificationOptions
import de.jensklingenberg.sheasy.web.usecase.NotificationUseCase
import react.RBuilder
import react.RComponent
import react.RProps
import react.RState
import react.setState


interface NotificationVState : RState {
    var openToolTip: Boolean
    var ignoreNotification: Boolean
    var notiOptions: NotificationOptions
    var notiTitle: String
}


class NotificationView : RComponent<RProps, NotificationVState>(), NotificationContract.View {
    private var presenter: NotificationContract.Presenter? = NotificationPresenter(this)
    val notificationUseCase= NotificationUseCase()


    override fun NotificationVState.init(props: RProps) {
        openToolTip = true
        ignoreNotification = true
        notiOptions = NotificationOptions()
        notiTitle = ""
    }

    override fun componentDidMount() {

        presenter?.componentDidMount()
    }


    private fun handleChange() {
        setState {
            openToolTip = !openToolTip
        }
    }



    override fun showNotification(reactNotificationOptions: NotificationOptions) {

        setState {
            notiTitle = reactNotificationOptions.title ?: ""
            notiOptions = reactNotificationOptions
            console.log("HKjldfjkslajflajslkjdfs " + ignoreNotification)
            this.ignoreNotification = false
        }

    }

    override fun componentWillUnmount() {
        presenter?.componentWillUnmount()
       // super.componentWillUnmount()

    }


    override fun RBuilder.render() {

        if(state.ignoreNotification==false){
            notificationUseCase.showNotification(this,state.notiOptions)

        }

    }

}

fun RBuilder.NotificationView() = child(NotificationView::class) {}