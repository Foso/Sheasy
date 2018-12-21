package ui.apps


import components.materialui.*
import components.materialui.icons.BlockIcon
import components.materialui.icons.DownloadIcon
import components.reactstrap.FormGroup
import components.reactstrap.Input
import components.reactstrap.Row
import data.StringResource.Companion.APPS_OVERVIEW_TABLE_ROW_HEADER_DOWNLOAD
import data.StringResource.Companion.APPS_OVERVIEW_TABLE_ROW_HEADER_ICON
import data.StringResource.Companion.APPS_OVERVIEW_TABLE_ROW_HEADER_NAME
import kotlinx.html.InputType
import kotlinx.html.js.onClickFunction
import model.AppResponse
import model.Error
import model.Status
import network.NetworkUtil
import org.w3c.dom.HTMLInputElement
import react.*
import react.dom.div
import ui.common.styleProps
import ui.common.toolbar
import kotlin.browser.window


interface AppsVState : RState {
    var appsResult: List<AppResponse>
    var errorMessage: String
    var status: Status

}


class AppsView : RComponent<RProps, AppsVState>(), AppsContract.View {

    private var presenter: AppsPresenter? = null

    /****************************************** React Lifecycle methods  */
    override fun AppsVState.init() {
        appsResult = emptyList()
        status = Status.LOADING
    }

    override fun componentDidMount() {
        presenter = AppsPresenter(this)
        presenter?.getApps()
    }

    override fun RBuilder.render() {
        toolbar()

        FormGroup {
            Input {
                attrs {
                    type = InputType.search.realValue
                    name = "search"
                    placeholder = "SEARCH HERE"
                    onChange = {
                        presenter?.onSearch((it.target as HTMLInputElement).value)
                    }
                }
            }
        }
        Paper {
            attrs {
                elevation = 1
            }
            Table {
                TableHead {
                    TableRow {
                        listOf(
                            APPS_OVERVIEW_TABLE_ROW_HEADER_ICON,
                            APPS_OVERVIEW_TABLE_ROW_HEADER_NAME,
                            APPS_OVERVIEW_TABLE_ROW_HEADER_DOWNLOAD
                        ).forEach {
                            TableCell {
                                div {
                                    +it
                                    attrs {
                                        styleProps(textAlign = "center")
                                    }
                                }
                            }
                        }
                    }
                }
                TableBody {
                    state.appsResult.forEach { app ->
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
                                                    NetworkUtil.appDownloadUrl(app.packageName)
                                        }
                                        styleProps(textAlign = "center")
                                    }
                                }
                            }


                        }
                        Row {
                            Divider {}
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
                open = snackbarVisibility()
                message = state.errorMessage
                autoHideDuration = 6000
                onClose = { false }
                variant = "error"
            }
        }
    }

    /****************************************** Presenter methods  */

    override fun showError(error: Error) {
        setState {
            when (error) {
                Error.NETWORK_ERROR -> {
                    status = Status.ERROR
                    state.errorMessage = "No Connection"
                }
                Error.NOT_AUTHORIZED -> {
                    status = Status.ERROR
                    state.errorMessage = "Device is not authorized"
                }
            }
        }

    }

    override fun setData(apps: List<AppResponse>) {
        setState {
            status = Status.SUCCESS
            appsResult = apps
        }
    }

    /****************************************** Class methods  */

    fun snackbarVisibility(): Boolean {
        return when (state.status) {
            Status.ERROR -> {
                true
            }
            else -> {
                false
            }
        }
    }

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
}


fun RBuilder.appsView() = child(AppsView::class) {}