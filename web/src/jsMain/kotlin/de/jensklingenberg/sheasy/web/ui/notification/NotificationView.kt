package de.jensklingenberg.sheasy.web.ui.notification


import components.materialui.Button
import de.jensklingenberg.sheasy.web.components.Notification.Notification
import de.jensklingenberg.sheasy.web.components.Notification.ReactNotificationOptions
import de.jensklingenberg.sheasy.web.components.Notification.defaultReactNotificationOptions
import de.jensklingenberg.sheasy.web.components.reactstrap.Tooltip
import react.*


interface NotificationVState : RState {
    var openToolTip: Boolean
    var ignoreNotification: Boolean
    var notiOptions: ReactNotificationOptions
    var notiTitle: String
}


class NotificationView : RComponent<RProps, NotificationVState>(), NotificationContract.View {
    private var presenter: NotificationContract.Presenter? = null


    override fun NotificationVState.init(props: RProps) {
        openToolTip = true
        ignoreNotification = true
        notiOptions = defaultReactNotificationOptions
        notiTitle = ""
    }

    override fun componentDidMount() {
        presenter = NotificationPresenter(this)
        presenter?.componentDidMount()
    }


    private fun handleChange() {
        setState {
            openToolTip = !openToolTip
        }
    }


    override fun showNotification(reactNotificationOptions: ReactNotificationOptions) {

        setState {
            notiTitle = reactNotificationOptions.title ?: ""
            notiOptions = reactNotificationOptions
            console.log("HKjldfjkslajflajslkjdfs" + ignoreNotification)
            this.ignoreNotification = false
        }

    }


    override fun RBuilder.render() {
        Button {
            +"Notification"
            attrs {
               
            }
        }

        Tooltip {
            +"HHHH"
            attrs {
                placement = "top"
                target = "HALLOID"
                toggle = { handleChange() }
                isOpen = this@NotificationView.state.openToolTip
            }
        }

        Notification {
            attrs {
                title = state.notiTitle
                timeout = 5000
                options = state.notiOptions

                console.log(this@NotificationView.state.ignoreNotification)
            }
        }
    }

}

fun RBuilder.NotificationView() = child(NotificationView::class) {}