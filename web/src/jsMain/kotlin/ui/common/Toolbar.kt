package ui.common

import components.materialui.*
import components.materialui.icons.MenuIcon
import model.DrawerItems
import react.*
import react.dom.div
import ui.media.MediaView
import ui.notification.NotificationView


interface ToolbarState : RState {
    var open: Boolean
}

class BaseToolbar : RComponent<RProps, ToolbarState>() {


    override fun ToolbarState.init(props: RProps) {
        open = false

    }


    override fun componentDidMount() {}

    override fun componentWillUnmount() {}

    private fun handleChange() {
        setState {
            open = !open
        }

    }

    override fun RBuilder.render() {

        Drawer {
            attrs {
                open = this@BaseToolbar.state.open
                onClose = { handleChange() }
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
                                style = kotlinext.js.js {
                                    textAlign = "center"
                                }
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

                        onClick = { handleChange() }

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
                            }
                        }
                        attrs {
                            item = true
                            xs = 6
                        }
                    }
                    Grid {
                        div {
                            attrs {
                                styleProps(textAlign = "right")
                            }
                            MediaView()
                            NotificationView()

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
}

fun RBuilder.toolbar() = child(BaseToolbar::class) {
}
