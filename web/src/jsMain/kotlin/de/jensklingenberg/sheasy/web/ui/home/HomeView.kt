package de.jensklingenberg.sheasy.web.ui.home

import de.jensklingenberg.sheasy.web.components.materialui.List
import de.jensklingenberg.sheasy.web.components.materialui.ListItem
import components.materialui.ListItemText
import de.jensklingenberg.sheasy.web.model.DrawerItems
import react.RBuilder
import react.RComponent
import react.RProps
import react.RState
import de.jensklingenberg.sheasy.web.ui.common.styleProps
import de.jensklingenberg.sheasy.web.ui.common.toolbar

class HomeView : RComponent<RProps, RState>() {

    override fun RBuilder.render() {
        toolbar()

        DrawerItems
            .values()
            .filterNot { it == DrawerItems.SCREENSHARE || it==DrawerItems.HOME }
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


fun RBuilder.app() = child(HomeView::class) {}




