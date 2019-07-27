package de.jensklingenberg.sheasy.web.ui.share

import components.materialui.ListItemText
import de.jensklingenberg.sheasy.model.ShareItem
import de.jensklingenberg.sheasy.model.ShareType
import de.jensklingenberg.sheasy.web.components.materialui.List
import de.jensklingenberg.sheasy.web.components.materialui.ListItem
import de.jensklingenberg.sheasy.web.model.SourceItem
import de.jensklingenberg.sheasy.web.ui.common.styleProps
import react.RBuilder
import react.dom.p
import kotlin.js.Date

class ShareSourceItem(val shareItem: ShareItem, val type: ShareType) : SourceItem() {

    override fun render(rBuilder: RBuilder) {

        val date = Date()
        val timeString = date.getHours().toString() + ":" + date.getMinutes() + ":" + date.getSeconds()

        with(rBuilder) {
            List {
                attrs {
                    component = "nav"
                }
                p {
                    +timeString

                }

                ListItem {
                    attrs {
                        href = shareItem.message
                        component = "a"
                        divider = true
                        if (this@ShareSourceItem.type == ShareType.INCOMING) {
                            styleProps(textAlign = "left")
                        } else {
                            styleProps(textAlign = "right")

                        }
                    }

                    ListItemText {
                        attrs {
                            this.primary = shareItem.message
                        }
                    }
                }
            }

        }

    }

}