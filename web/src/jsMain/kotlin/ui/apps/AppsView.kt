package ui.apps

import components.materialui.Divider
import components.materialui.icons.DeleteIcon
import components.materialui.icons.DownloadIcon
import components.reactstrap.*
import data.StringResource.Companion.APPS_OVERVIEW_TABLE_ROW_HEADER_DOWNLOAD
import data.StringResource.Companion.APPS_OVERVIEW_TABLE_ROW_HEADER_ICON
import data.StringResource.Companion.APPS_OVERVIEW_TABLE_ROW_HEADER_NAME
import data.StringResource.Companion.APPS_OVERVIEW_TABLE_ROW_HEADER_UNINSTALL
import kotlinx.html.InputType
import kotlinx.html.js.onClickFunction
import kotlinx.html.style
import model.AppResponse
import network.NetworkUtil
import org.w3c.dom.HTMLInputElement
import react.*
import react.dom.div
import react.dom.img
import ui.toolbar
import kotlin.browser.window


interface AppsVState : RState {
    var appsResult: List<AppResponse>
    var errorMessage: String
}


class AppsView : RComponent<RProps, AppsVState>(), AppsContract.View {
    private var presenter: AppsPresenter? = null

    override fun setData(apps: List<AppResponse>) {
        setState {
            appsResult = apps
        }

    }

    override fun AppsVState.init() {
        appsResult = emptyList()
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

        Container {

            Row {
                listOf(
                    APPS_OVERVIEW_TABLE_ROW_HEADER_ICON,
                    APPS_OVERVIEW_TABLE_ROW_HEADER_NAME,
                    APPS_OVERVIEW_TABLE_ROW_HEADER_DOWNLOAD,
                    APPS_OVERVIEW_TABLE_ROW_HEADER_UNINSTALL
                ).forEach {
                    Col {
                        div {
                            +it
                            attrs {
                                style = kotlinext.js.js {
                                    textAlign = "center"
                                }

                            }
                        }
                    }
                }

            }

            state.appsResult.forEach { app ->
                Row {
                    Col {
                        initAppIconView(app)
                    }
                    Col {

                        div {
                            +app.name
                            attrs {
                                style = kotlinext.js.js {
                                    textAlign = "center"
                                }

                            }
                        }
                    }
                    Col {
                        initDownloadButton(DownloadIcon, app)
                    }
                    Col {
                        initDeleteButton(DeleteIcon, app)
                    }
                    Divider {
                        attrs {
                            this.light = true
                        }

                    }
                }
            }


        }


    }

    private fun RElementBuilder<ColProps>.initAppIconView(item: AppResponse) {
        div {
            img {
                attrs {
                    src = NetworkUtil.appIconUrl(item.packageName)
                    height = "50"
                    width = "50"
                    style = kotlinext.js.js {
                        display = "block"
                        margin = 0
                    }

                }
            }
        }
    }

    private fun RElementBuilder<ColProps>.initDownloadButton(DownloadIcon: RClass<dynamic>, item: AppResponse) {
        div {
            DownloadIcon {

            }
            attrs {
                onClickFunction = {
                    window.location.href =
                            NetworkUtil.appDownloadUrl(item.packageName)
                }
                style = kotlinext.js.js {
                    textAlign = "center"
                }


            }
        }
    }

    private fun RElementBuilder<ColProps>.initDeleteButton(DeleteIcon: RClass<dynamic>, item: AppResponse) {
        div {
            DeleteIcon {

            }
            attrs {
                onClickFunction = {
                    window.location.href =
                            NetworkUtil.appDownloadUrl(item.packageName)
                }
                style = kotlinext.js.js {
                    textAlign = "center"
                }


            }
        }
    }


}

fun RBuilder.appsView() = child(AppsView::class) {

}