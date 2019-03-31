package de.jensklingenberg.sheasy.web.ui.common

import components.materialui.IconButton
import de.jensklingenberg.sheasy.web.components.materialui.ListItem
import components.materialui.ListItemIcon
import components.materialui.ListItemText
import components.materialui.TableCell
import components.materialui.TableRow
import components.materialui.icons.AndroidIcon
import components.materialui.icons.BlockIcon
import components.materialui.icons.DownloadIcon
import components.materialui.icons.FolderIcon
import components.materialui.icons.MoreVertIcon
import de.jensklingenberg.sheasy.model.FileResponse
import de.jensklingenberg.sheasy.web.network.ApiEndPoint
import de.jensklingenberg.sheasy.web.model.response.App
import kotlinx.html.js.onClickFunction
import org.w3c.dom.events.Event
import react.RBuilder
import react.dom.div
import react.dom.p
import kotlin.browser.window
import  de.jensklingenberg.sheasy.web.components.materialui.List

class ListItemBuilder{
    companion object {

        fun listItem(rBuilder: RBuilder, file: FileResponse, itemClickFunction:(Event)->Unit, onMoreBtnClick:(Event)->Unit) {
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

        fun listItem(rBuilder: RBuilder, app: App, itemClickFunction:(Event)->Unit, onMoreBtnClick:(Event)->Unit) {
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