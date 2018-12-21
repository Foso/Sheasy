package ui.home

import components.materialui.List
import components.materialui.ListItem
import components.materialui.ListItemText
import model.DrawerItems
import react.RBuilder
import react.RComponent
import react.RProps
import react.RState
import ui.common.styleProps
import ui.common.toolbar

class App : RComponent<RProps, RState>() {

    override fun RBuilder.render() {
        toolbar()

        DrawerItems
            .values()
            .forEach {
                List {
                    attrs {
                        component = "nav"
                    }
                    ListItem {
                        attrs {
                            href = it.destination
                            component = "a"
                            divider = true
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
}


fun RBuilder.app() = child(App::class) {}




