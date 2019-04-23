package de.jensklingenberg.sheasy.web.ui.apps

import components.materialui.IconButton
import components.materialui.ListItemIcon
import components.materialui.ListItemText
import components.materialui.icons.AndroidIcon
import components.materialui.icons.MoreVertIcon
import de.jensklingenberg.sheasy.web.components.materialui.List
import de.jensklingenberg.sheasy.web.components.materialui.ListItem
import de.jensklingenberg.sheasy.web.model.SourceItem
import de.jensklingenberg.sheasy.web.model.response.App
import de.jensklingenberg.sheasy.web.ui.common.styleProps
import kotlinx.html.js.onClickFunction
import org.w3c.dom.events.Event
import react.RBuilder
import react.dom.p


interface OnFileClickListener {

}

class AppSourceItem(val app: App, val itemClickFunction: (Event) -> Unit, val onMoreBtnClick: (Event) -> Unit) :
    SourceItem() {

    override fun render(rBuilder: RBuilder) {

        with(rBuilder) {
                ListItem {
                    attrs {
                        // href = presenter.getFilesUrl(it.path)
                        component = "a"
                        divider = true
                        styleProps(textAlign = "left")
                    }

                    ListItemIcon {
                        AndroidIcon {}
                    }

                    ListItemText {
                        p {
                            +app.name
                            attrs {
                                onClickFunction = itemClickFunction//{ presenter.setPath(file.path) }
                            }
                        }

                    }

                    IconButton {
                        MoreVertIcon {}
                        attrs {
                            asDynamic()["aria-owns"] = "simple-menu"
                            asDynamic()["aria-haspopup"] = true
                            onClick = onMoreBtnClick
                        }
                    }
                }
            }



    }

}