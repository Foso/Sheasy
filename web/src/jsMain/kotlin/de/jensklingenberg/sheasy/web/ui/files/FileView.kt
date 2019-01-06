package de.jensklingenberg.sheasy.web.ui.files

import components.materialui.Button
import components.materialui.CircularProgress
import components.materialui.InputLabel
import components.materialui.Menu
import components.materialui.MenuItem
import de.jensklingenberg.sheasy.web.components.materialui.Input
import de.jensklingenberg.sheasy.web.data.AppsDataSource
import de.jensklingenberg.sheasy.web.data.NetworkPreferences
import de.jensklingenberg.sheasy.web.data.repository.AppsRepository
import de.jensklingenberg.sheasy.web.model.Error
import de.jensklingenberg.sheasy.web.model.StringRes
import de.jensklingenberg.sheasy.web.model.response.FileResponse
import de.jensklingenberg.sheasy.web.model.response.Status
import de.jensklingenberg.sheasy.web.network.ApiEndPoint
import de.jensklingenberg.sheasy.web.network.ReactHttpClient
import de.jensklingenberg.sheasy.web.ui.common.BaseComponent
import de.jensklingenberg.sheasy.web.ui.common.ListItemBuilder
import de.jensklingenberg.sheasy.web.ui.common.extension.selectedFile
import de.jensklingenberg.sheasy.web.ui.common.styleProps
import de.jensklingenberg.sheasy.web.ui.common.toolbar
import de.jensklingenberg.sheasy.web.usecase.MessageUseCase
import kotlinx.html.DIV
import kotlinx.html.InputType
import kotlinx.html.js.onClickFunction
import org.w3c.dom.events.Event
import org.w3c.dom.events.EventTarget
import react.RBuilder
import react.RProps
import react.RState
import react.dom.RDOMBuilder
import react.dom.div
import react.setState
import kotlin.browser.window


interface FileViewState : RState {
    var filesList: List<FileResponse>
    var errorMessage: String
    var status: Status
    var openMenu: Boolean
    var anchor: EventTarget?
}


class FileView : BaseComponent<RProps, FileViewState>(), FilesContract.View {
    val appsDataSource: AppsDataSource = AppsRepository(ReactHttpClient(NetworkPreferences()))
    var presenter = FilesPresenter(this, appsDataSource)
    val messageUseCase = MessageUseCase()

    /****************************************** React Lifecycle methods  */

    override fun FileViewState.init() {
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
            setupBackButton(this)
            setupUploadButton(this)
        }
        div {
            Input {
                attrs {
                    styleProps(width = "100%")

                    type = InputType.search.realValue
                    value = presenter.folderPath
                }
            }
        }

        state.filesList
            .forEach { file ->
                ListItemBuilder.listItem(
                    rBuilder = this,
                    file = file,
                    itemClickFunction = { presenter.setPath(file.path) },
                    onMoreBtnClick = { event: Event -> handleClickListItem(event) })
            }

        div {
            CircularProgress {
                attrs {
                    styleProps(display = progressVisibiity())
                }
            }
        }

        messageUseCase.showSnackbar(this, state.errorMessage, errorsnackbarVisibility())

        setupContextMenu(this)

    }

    private fun setupContextMenu(rBuilder: RBuilder) {
        rBuilder.run {
            div {
                Menu {
                    attrs {
                        id = "simple-menu"
                        open = state.openMenu
                        anchorEl = state.anchor
                        onClose = {
                            run {
                                setState {
                                    openMenu = false
                                }
                            }
                        }


                    }
                    MenuItem {
                        div {
                            +"Download"
                            attrs {
                                onClickFunction = {
                                    window.location.href =
                                            ApiEndPoint.appDownloadUrl("de.jensklingenberg.sheasy")
                                }
                                styleProps(textAlign = "center")
                            }
                        }
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
    }


    private fun setupUploadButton(rdomBuilder: RDOMBuilder<DIV>) {
        rdomBuilder.run {
            Input {
                attrs {
                    id = "outlined-button-file"
                    type = "file"
                    styleProps(display = "none")
                    onChange = { event -> handleFile(event) }

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

    }

    fun handleFile(event: Event) {
        event.target.selectedFile?.let {
            console.log(it)

        }

    }

    private fun setupBackButton(rdomBuilder: RDOMBuilder<DIV>) {
        rdomBuilder.run {
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
                    state.errorMessage = StringRes.MESSAGE_NO_CONNECTION
                }
                else -> {
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
            openMenu = !openMenu
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


