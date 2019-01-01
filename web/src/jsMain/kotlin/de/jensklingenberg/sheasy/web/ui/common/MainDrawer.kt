package de.jensklingenberg.sheasy.web.ui.common

import components.materialui.Drawer
import components.materialui.DrawerProps
import components.materialui.EventHandlerFunction
import de.jensklingenberg.sheasy.web.data.StringResource
import react.RBuilder
import react.RComponent
import react.RState
import react.dom.a


interface MainDrawerState : RState {
    var open: Boolean
    var onClose: EventHandlerFunction?
}


class MainDrawer(props: DrawerProps) : RComponent<DrawerProps, MainDrawerState>(props) {

    override fun MainDrawerState.init(props: DrawerProps) {
        open = props.open
        onClose = props.onClose

    }


    override fun RBuilder.render() {
        Drawer {
            attrs {
                open = state.open
                onClose = state.onClose
            }

            a {
                +StringResource.TOOLBAR_HOME
                attrs {
                    href = Navigation.navigateToHome
                }
            }

            a {
                +StringResource.TOOLBAR_APPS
                attrs {
                    href = Navigation.navigateToApps
                }
            }

            a {
                +StringResource.TOOLBAR_ABOUT
                attrs {
                    href = Navigation.navigateToAbout
                }
            }

            a {
                +StringResource.DRAWER_SCREENSHARE
                attrs {
                    href = Navigation.navigateToScreenShare
                }
            }

        }


    }

}

fun RBuilder.mainDrawer(
    open: Boolean,
    onClose: EventHandlerFunction?
) = child(MainDrawer::class) {
    attrs {
        this.open = open
        this.onClose = onClose
    }
}

