package ui.notification


import components.Notification.Notification
import components.Notification.ReactNotificationOptions
import components.Notification.defaultReactNotificationOptions
import components.reactstrap.Button
import components.reactstrap.Tooltip
import react.*


interface NotificationVState : RState {
    var openToolTip: Boolean
    var ignoreNotification: Boolean
    var notiOptions: ReactNotificationOptions
    var notiTitle: String
}


class NotificationView : RComponent<RProps, NotificationVState>(), NotificationContract.View {


    override fun NotificationVState.init(props: RProps) {
        openToolTip = true
        ignoreNotification = true
        notiOptions = defaultReactNotificationOptions
        notiTitle = ""
    }


    private fun handleChange() {
        setState {
            openToolTip = !openToolTip
        }

    }


    override fun showNotification(text: ReactNotificationOptions) {

        setState {
            notiTitle = "JETZT GETH ER"
            notiOptions = text
            console.log("HKjldfjkslajflajslkjdfs" + ignoreNotification)

            this.ignoreNotification = false
        }

    }

    private var presenter: NotificationContract.Presenter? = null


    override fun componentDidMount() {
        presenter = NotificationPresenter(this)
        presenter?.componentDidMount()
    }

    override fun RBuilder.render() {
        Button {
            +"HHH"
            attrs {
                id = "HALLOID"
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

fun RBuilder.NotificationView() = child(NotificationView::class) {

}