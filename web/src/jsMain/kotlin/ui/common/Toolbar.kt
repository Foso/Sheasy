package ui.common

import components.materialui.*
import components.materialui.icons.MenuIcon
import model.DrawerItems
import react.*
import react.dom.div


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

        Drawer {
            attrs {
                open = this@BaseToolbar.state.open
                onClose = { toggleDrawer() }
            }

            DrawerItems
                .values()
                .forEach {
                    List {
                        attrs{
                            component="nav"
                        }
                        ListItem {
                            attrs {
                                href = it.destination
                                component = "a"
                                divider=true
                                styleProps(textAlign = "center")
                            }

                            ListItemText {
                                attrs {
                                    this.primary = it.title
                                }
                            }
                        }
                    }
                }
        }

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

                          //  MediaView()
                          //  NotificationView()

                        }
                        attrs {
                            item = true
                            xs = 6
                        }
                    }
                }


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
