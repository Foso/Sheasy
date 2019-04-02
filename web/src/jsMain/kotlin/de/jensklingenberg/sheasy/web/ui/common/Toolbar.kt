package de.jensklingenberg.sheasy.web.ui.common

import components.materialui.AppBar
import components.materialui.Grid
import components.materialui.IconButton
import components.materialui.ListItemIcon
import components.materialui.Toolbar
import components.materialui.Typography
import components.materialui.icons.FolderIcon
import components.materialui.icons.MenuIcon
import de.jensklingenberg.sheasy.web.components.materialui.icons.NotificationsOffIcon
import de.jensklingenberg.sheasy.web.model.DrawerItems
import de.jensklingenberg.sheasy.web.ui.notification.NotificationView
import react.RBuilder
import react.RComponent
import react.RProps
import react.RState
import react.dom.div
import react.setState


interface ToolbarState : RState {
    var open: Boolean
}

class BaseToolbar : RComponent<RProps, ToolbarState>() {

    /****************************************** React Lifecycle methods  */

    override fun ToolbarState.init(props: RProps) {
        open = false

    }

    override fun componentDidMount() {}

    override fun componentWillUnmount() {}

    override fun RBuilder.render() {

        Drawer.setupDrawer(
            this, DrawerItems
                .values(),this@BaseToolbar.state.open,{toggleDrawer()}
        )
        setupAppBar(this)

    }

    private fun setupAppBar(rBuilder: RBuilder) {
        rBuilder.run {
            AppBar {
                attrs {
                    position = "static"
                }
                Toolbar {
                    IconButton {
                        attrs {
                            color = "inherit"

                            onClick = { toggleDrawer() }

                        }
                        MenuIcon {

                        }

                    }
                    Grid {
                        attrs {
                            container = true
                            spacing = 12
                        }
                        Grid {
                            Typography {
                                +"Sheasy"
                                attrs {
                                    color = "inherit"
                                    styleProps(textAlign = "right")
                                }
                            }

                            attrs {
                                item = true
                                xs = 6
                            }
                        }
                        Grid {
                            div {
                                ListItemIcon {
                                    NotificationsOffIcon {}
                                }
                                //  MediaView()
                                de.jensklingenberg.sheasy.web.ui.notification.NotificationView()

                            }
                            attrs {
                                item = true
                                xs = 6
                            }
                        }
                    }


                }
                NotificationView()

            }

        }

    }



    /****************************************** Class methods  */

    private fun toggleDrawer() {
        setState {
            open = !open
        }

    }
}

fun RBuilder.toolbar() = child(BaseToolbar::class) {
}
