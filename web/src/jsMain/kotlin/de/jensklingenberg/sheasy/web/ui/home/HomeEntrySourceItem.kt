package de.jensklingenberg.sheasy.web.ui.home

import components.materialui.ListItemText
import de.jensklingenberg.sheasy.web.components.materialui.List
import de.jensklingenberg.sheasy.web.components.materialui.ListItem
import de.jensklingenberg.sheasy.web.model.HomeItem
import de.jensklingenberg.sheasy.web.model.SourceItem
import de.jensklingenberg.sheasy.web.ui.common.styleProps
import react.RBuilder

class HomeEntrySourceItem(val homeItem: HomeItem) : SourceItem() {

    override fun render(rBuilder: RBuilder) {
        with(rBuilder) {
            List {
                attrs {
                    component = "nav"
                }
                ListItem {
                    attrs {
                        href = homeItem.destination
                        component = "a"
                        divider = true
                        styleProps(textAlign = "center")
                    }

                    ListItemText {
                        attrs {
                            this.primary = homeItem.title
                        }
                    }
                }
            }

        }

    }

}