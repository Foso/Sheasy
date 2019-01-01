package de.jensklingenberg.sheasy.web.ui.apps


import components.materialui.*
import de.jensklingenberg.sheasy.web.network.ApiEndPoint
import de.jensklingenberg.sheasy.web.network.ReactHttpClient
import components.reactstrap.FormGroup
import components.reactstrap.Input
import components.reactstrap.Row
import de.jensklingenberg.sheasy.web.data.StringResource.Companion.APPS_OVERVIEW_TABLE_ROW_HEADER_DOWNLOAD
import de.jensklingenberg.sheasy.web.data.StringResource.Companion.APPS_OVERVIEW_TABLE_ROW_HEADER_ICON
import de.jensklingenberg.sheasy.web.data.StringResource.Companion.APPS_OVERVIEW_TABLE_ROW_HEADER_NAME
import de.jensklingenberg.sheasy.web.data.repository.AppsRepository
import de.jensklingenberg.sheasy.web.model.Error
import de.jensklingenberg.sheasy.web.model.response.App
import de.jensklingenberg.sheasy.web.model.response.Status
import de.jensklingenberg.sheasy.web.data.AppsDataSource
import de.jensklingenberg.sheasy.web.ui.common.ListItemBuilder
import de.jensklingenberg.sheasy.web.usecase.MessageUseCase
import de.jensklingenberg.sheasy.web.ui.common.styleProps
import de.jensklingenberg.sheasy.web.ui.common.toolbar
import kotlinx.html.InputType
import org.w3c.dom.HTMLInputElement
import react.*
import react.dom.div


interface AppsVState : RState {
    var appsResult: List<App>
    var errorMessage: String
    var status: Status
}


class AppsView : RComponent<RProps, AppsVState>(), AppsContract.View {

    private var presenter: AppsPresenter? = null
    val appsDataSource: AppsDataSource = AppsRepository(ReactHttpClient())
    val messageUseCase= MessageUseCase()

    /****************************************** React Lifecycle methods  */
    override fun AppsVState.init() {
        appsResult = emptyList()
        status = Status.LOADING
    }

    override fun componentDidMount() {
        presenter = AppsPresenter(this, appsDataSource)
        presenter?.getApps()
    }

    override fun RBuilder.render() {

        toolbar()

        setupSearchBar(this)

        Paper {
            attrs {
                elevation = 1
            }
            Table {
                renderTableHeader(this,listOf(
                    APPS_OVERVIEW_TABLE_ROW_HEADER_ICON,
                    APPS_OVERVIEW_TABLE_ROW_HEADER_NAME,
                    APPS_OVERVIEW_TABLE_ROW_HEADER_DOWNLOAD
                ))
                renderTableBody(this)
            }

        }
        div {
            CircularProgress {
                attrs {
                    styleProps(display = progressVisibiity())
                }
            }
        }

        messageUseCase.showSnackbar(this,state.errorMessage,snackbarVisibility())

    }

    private fun setupSearchBar(rBuilder: RBuilder) {
        rBuilder.run {
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

    override fun setData(apps: List<App>) {
        setState {
            status = Status.SUCCESS
            appsResult = apps
        }
    }

    /****************************************** Class methods  */


    private fun renderTableBody(rElementBuilder: RElementBuilder<TableProps>) {
        rElementBuilder.run {
            TableBody {

                state.appsResult.forEach { app ->
                    ListItemBuilder.listItem(this, app)
                    Row {
                        Divider {}
                    }
                }
            }
        }
    }



    private fun renderTableHeader(
        rElementBuilder: RElementBuilder<TableProps>,
        listOf: List<String>
    ) {
        with(rElementBuilder) {
            TableHead {
                TableRow {
                    listOf.forEach {
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
        }

    }


    private fun snackbarVisibility(): Boolean {
        return when (state.status) {
            Status.ERROR -> {
                true
            }
            else -> {
                false
            }
        }
    }

    private fun progressVisibiity(): String {
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