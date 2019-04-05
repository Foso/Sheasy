package de.jensklingenberg.sheasy.web.ui.share

import components.materialui.ListItemText
import de.jensklingenberg.sheasy.web.components.materialui.List
import de.jensklingenberg.sheasy.web.components.materialui.ListItem
import de.jensklingenberg.sheasy.web.model.OnEntryClickListener
import de.jensklingenberg.sheasy.web.model.SourceItem
import de.jensklingenberg.sheasy.web.ui.common.styleProps
import de.jensklingenberg.sheasy.web.ui.home.HomeItem
import react.RBuilder

class ShareSourceItem(val homeItem: HomeItem, var onEntryClickListener: OnEntryClickListener? = null) : SourceItem() {

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