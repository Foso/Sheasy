package de.jensklingenberg.sheasy.web.ui.apps


import components.materialui.CircularProgress
import components.materialui.Divider
import components.materialui.FormControl
import components.materialui.Menu
import components.materialui.MenuItem
import components.materialui.Paper
import components.materialui.TableCell
import components.materialui.TableHead
import components.materialui.TableProps
import components.materialui.TableRow
import de.jensklingenberg.sheasy.web.components.materialui.Input
import de.jensklingenberg.sheasy.web.data.FileDataSource
import de.jensklingenberg.sheasy.web.data.NetworkPreferences
import de.jensklingenberg.sheasy.web.data.repository.FileRepository
import de.jensklingenberg.sheasy.web.model.Error
import de.jensklingenberg.sheasy.web.model.response.App
import de.jensklingenberg.sheasy.web.model.response.Status
import de.jensklingenberg.sheasy.web.network.ReactHttpClient
import de.jensklingenberg.sheasy.web.ui.common.BaseComponent
import de.jensklingenberg.sheasy.web.ui.common.ListItemBuilder
import de.jensklingenberg.sheasy.web.ui.common.styleProps
import de.jensklingenberg.sheasy.web.ui.common.toolbar
import de.jensklingenberg.sheasy.web.usecase.MessageUseCase
import kotlinx.html.InputType
import org.w3c.dom.HTMLInputElement
import org.w3c.dom.events.Event
import org.w3c.dom.events.EventTarget
import react.RBuilder
import react.RElementBuilder
import react.RProps
import react.RState
import react.dom.div
import react.setState


interface AppsViewState : RState {
    var appsResult: List<App>
    var errorMessage: String
    var status: Status
    var openMenu: Boolean
    var anchor: EventTarget?
}



class AppsView : BaseComponent<RProps, AppsViewState>(), AppsContract.View {

    private var presenter: AppsPresenter? = null
    val appsDataSource: FileDataSource = FileRepository(ReactHttpClient(NetworkPreferences()))

    val messageUseCase= MessageUseCase()

    /****************************************** React Lifecycle methods  */


    override fun AppsViewState.init() {
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

            state.appsResult.forEach { app ->
                ListItemBuilder.listItem(
                    rBuilder = this,
                    app = app,
                    itemClickFunction = {  },
                    onMoreBtnClick = { event: Event -> handleClickListItem(event) })

                Divider {}

            }

        }
        div {
            CircularProgress {
                attrs {
                    styleProps(display = progressVisibiity())
                }
            }
        }

        messageUseCase.showErrorSnackbar(this,state.errorMessage,snackbarVisibility())
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

                        +"Download"
                        attrs {
                            styleProps(textAlign = "right")
                            onClick = { event: Event -> handleMenuItemClick(event) }
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

    private fun setupSearchBar(rBuilder: RBuilder) {
        rBuilder.run {
            FormControl {
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
                Error.NetworkError() -> {
                    status = Status.ERROR
                    state.errorMessage = "No Connection"
                }
                Error.NotAuthorizedError() -> {
                    status = Status.ERROR
                    state.errorMessage = "Device is not authorized"
                }
                else -> {
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
    fun handleClickListItem(event: Event) {
        val currentTarget = event.currentTarget
        setState {
            openMenu = !openMenu
            anchor = currentTarget
        }

    }

    private fun renderTableBody(rElementBuilder: RElementBuilder<TableProps>) {
        rElementBuilder.run {


                state.appsResult.forEach { app ->
                    ListItemBuilder.listItem(
                        rBuilder = this,
                        app = app,
                        itemClickFunction = {  },
                        onMoreBtnClick = {  })

                        Divider {}

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

    fun handleMenuItemClick(event: Event) {
        setState {
            openMenu = false
            anchor = null
        }
    }


}


fun RBuilder.appsView() = child(AppsView::class) {}