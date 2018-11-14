package app


import components.Draggable
import components.materialui.BottomNavigation
import components.materialui.BottomNavigationAction
import components.materialui.Button
import components.materialui.icons.DeleteIcon
import react.RBuilder
import react.RComponent
import react.RProps
import react.RState
import react.dom.a
import react.dom.div
import ui.common.Navigation
import ui.header
import ui.notification.NotificationView
import ui.toolbar

interface AppState : RState {
    var open: Boolean
}


class App : RComponent<RProps, AppState>() {


    override fun RBuilder.render() {
        toolbar()

        Draggable {
            div("react-draggable") {
                +"DRAGi"


            }
        }

        a{
            +"HOME"
            attrs {
                href= Navigation.navigateToHome
            }
        }

        a{
            +"APPS"
            attrs {
                href= Navigation.navigateToApps
            }
        }



    }
}

fun RBuilder.app() = child(App::class) {}


