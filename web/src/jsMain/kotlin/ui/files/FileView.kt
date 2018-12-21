package ui.files

import components.materialui.*
import components.materialui.icons.FolderIcon
import components.materialui.icons.MoreVertIcon
import kotlinx.html.InputType
import kotlinx.html.js.onClickFunction
import kotlinx.html.onClick
import model.Error
import model.FileResponse
import model.Status
import org.w3c.dom.events.Event
import org.w3c.dom.events.EventTarget
import react.*
import react.dom.div
import react.dom.p
import ui.common.styleProps
import ui.common.toolbar


interface FilesState : RState {
    var filesList: List<FileResponse>
    var errorMessage: String
    var status: Status
    var openMenu: Boolean
    var anchor: EventTarget?
}


class FileView : RComponent<RProps, FilesState>(), FilesContract.View {

    val presenter = FilesPresenter(this)

    /****************************************** React Lifecycle methods  */

    override fun FilesState.init() {
        filesList = emptyList()
        status = Status.LOADING
        openMenu = false
        anchor = null

    }

    override fun componentDidMount() {
        presenter.getFiles()
    }

    override fun RBuilder.render() {


        toolbar()

        div {
            Button {
                +"Back"
                attrs {
                    variant = "outlined"
                    component = "span"

                    onClick = {
                        presenter.navigateUp()
                    }

                }

            }
            Input {
                attrs {
                    id = "outlined-button-file"
                    type = "file"
                    styleProps(display = "none")
                }
            }
            InputLabel {
                attrs {
                    htmlFor = "outlined-button-file"

                }
                Button {
                    +"Upload"
                    attrs {
                        component = "span"
                        variant = "outlined"

                    }
                }
            }


        }
        div {
            Input {
                attrs {
                    styleProps(width = "100%")

                    type = InputType.search.realValue
                    value = presenter.path
                }
            }
        }

        state.filesList
            .forEach { file ->
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

                                    onClickFunction = { presenter.setPath(file.path) }

                                }
                            }

                        }

                        IconButton {
                            MoreVertIcon {}
                            attrs {
                                asDynamic()["aria-owns"] = "simple-menu"
                                asDynamic()["aria-haspopup"] = true

                                onClick = { event: Event -> handleClickListItem(event) }
                            }
                        }
                    }
                }
            }

        div {
            CircularProgress {
                attrs {
                    styleProps(display = progressVisibiity())
                }
            }
        }

        Snackbar {
            attrs {
                this.anchorOrigin = object : SnackbarOrigin {
                    override var horizontal: String? = "center"
                    override var vertical: String? = "bottom"
                }
                open = errorsnackbarVisibility()
                message = state.errorMessage
                autoHideDuration = 6000
                onClose = { false }
                variant = "error"
            }
        }

        div {
            Menu {
                attrs {
                    id = "simple-menu"
                    open = state.openMenu
                    anchorEl = state.anchor

                }
                MenuItem {
                    +"Profile"
                    attrs {
                        styleProps(textAlign = "right")
                        onClick = { event: Event -> handleMenuItemClick(event) }
                    }
                }
            }
        }

    }

    /****************************************** Presenter methods  */

    override fun setData(filesResult: List<FileResponse>) {
        setState {
            status = Status.SUCCESS
            filesList = filesResult

        }
    }

    override fun showError(error: Error) {
        setState {
            when (error) {
                Error.NETWORK_ERROR -> {
                    status = Status.ERROR
                    state.errorMessage = "No Connection"
                }
            }
        }
    }

    /****************************************** Class methods  */

    fun errorsnackbarVisibility(): Boolean = state.status == Status.ERROR

    fun progressVisibiity(): String {
        return when (state.status) {
            Status.LOADING -> {
                ""
            }
            Status.SUCCESS, Status.ERROR -> {
                "none"
            }
        }
    }

    fun handleClickListItem(event: Event) {
        val currentTarget = event.currentTarget
        setState {
            openMenu = true
            anchor = currentTarget
        }

    }

    fun handleMenuItemClick(event: Event) {
        setState {
            openMenu = false
            anchor = null
        }
    }

}
