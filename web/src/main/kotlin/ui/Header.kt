package ui

import components.materialui.IconButton
import components.materialui.icons.MenuIcon
import components.reactstrap.*
import react.RBuilder
import react.RComponent
import react.RProps
import react.RState
import ui.apps.apps
import ui.media.MediaView
import ui.notification.NotificationView

interface TickerProps : RProps {
    var startFrom: Int
}

interface TickerState : RState {
    var modalIsOpen: Boolean
}

class Header(props: TickerProps) : RComponent<TickerProps, TickerState>(props) {
    override fun TickerState.init(props: TickerProps) {
        modalIsOpen = false
    }


    override fun componentDidMount() {

    }

    override fun componentWillUnmount() {

    }

    override fun RBuilder.render() {
        Navbar {
            attrs {
                color = "faded"
                expand = "md"
            }

            NavbarBrand {
                IconButton {
                    attrs {
                        color = "inherit"

                        //onClick={handleChange()}

                    }
                    MenuIcon {

                    }

                }
            }

            Nav {
                attrs {
                    className = NavClassName.ML_AUTO.value
                }

                NavItem {
                    NavLink {
                        apps()
                    }
                }

                NavItem {
                    NavLink {
                        MediaView()


                    }
                }

                NavItem {
                    NavLink {
                        NotificationView()


                    }
                }


            }


        }

    }
}

fun RBuilder.header() = child(Header::class) {
}
