package de.jensklingenberg.sheasy.web.ui.common

import components.materialui.*
import components.materialui.icons.BlockIcon
import components.materialui.icons.DownloadIcon
import components.materialui.icons.FolderIcon
import components.materialui.icons.MoreVertIcon
import de.jensklingenberg.sheasy.web.network.ApiEndPoint
import de.jensklingenberg.sheasy.web.model.response.App
import de.jensklingenberg.sheasy.web.model.response.FileResponse
import kotlinx.html.js.onClickFunction
import org.w3c.dom.events.Event
import react.RBuilder
import react.dom.div
import react.dom.p
import kotlin.browser.window

class ListItemBuilder{
    companion object {

        fun listItem(rBuilder: RBuilder, file: FileResponse, itemClickFunction:(Event)->Unit, onMoreBtnClick:(Event)->Unit) {
            with(rBuilder) {
                components.materialui.List {
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
                                +file.name
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


        fun listItem(rBuilder: RBuilder, app: App){
            with(rBuilder){
                TableRow {
                    TableCell {
                        div {
                            attrs {
                                styleProps(textAlign = "center")
                            }

                            BlockIcon {

                            }
                        }
                    }
                    TableCell {

                        div {
                            +app.name
                            attrs {
                                styleProps(textAlign = "center")

                            }
                        }
                    }
                    TableCell {
                        div {
                            DownloadIcon {

                            }
                            attrs {
                                onClickFunction = {
                                    window.location.href =
                                        ApiEndPoint.appDownloadUrl(app.packageName)
                                }
                                styleProps(textAlign = "center")
                            }
                        }
                    }


                }

            }
        }

    }
}