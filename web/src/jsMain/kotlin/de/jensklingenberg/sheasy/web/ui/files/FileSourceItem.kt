package de.jensklingenberg.sheasy.web.ui.files

import components.materialui.IconButton
import components.materialui.ListItemIcon
import components.materialui.ListItemText
import components.materialui.icons.FolderIcon
import components.materialui.icons.MoreVertIcon
import de.jensklingenberg.sheasy.model.FileResponse
import de.jensklingenberg.sheasy.web.components.materialui.List
import de.jensklingenberg.sheasy.web.components.materialui.ListItem
import de.jensklingenberg.sheasy.web.model.OnEntryClickListener
import de.jensklingenberg.sheasy.web.model.SourceItem
import de.jensklingenberg.sheasy.web.ui.common.styleProps
import kotlinx.html.js.onClickFunction
import org.w3c.dom.events.Event
import react.RBuilder
import react.dom.p


interface OnFileClickListener{

}

class FileSourceItem(val fileResponse: FileResponse, val itemClickFunction:(Event)->Unit, val onMoreBtnClick:(Event)->Unit) : SourceItem() {

    override fun render(rBuilder: RBuilder) {

        with(rBuilder) {
            List {
                attrs {
                    component = "nav"
                }

                ListItem {
                    attrs {
                        // href = presenter.getFiles(it.path)
                        component = "a"
                        divider = true
                        styleProps(textAlign = "left")
                    }

                    ListItemIcon {
                        FolderIcon {}
                    }

                    ListItemText {
                        p {
                            +fileResponse.name
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

}