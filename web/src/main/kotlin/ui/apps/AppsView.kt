package ui.apps

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

import model.AppFile
import network.NetworkUtil
import org.w3c.dom.HTMLInputElement
import react.*
import react.dom.div
import react.dom.img
import react.materialui.Divider
import ui.toolbar
import kotlin.browser.window


interface AppsVState : RState {
    var appsResult: List<AppFile>
    var errorMessage: String
}


class AppsView : RComponent<RProps, AppsVState>(), AppsContract.View {
    private var presenter: AppsPresenter? = null

    override fun setData(apps: List<AppFile>) {
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
                Col {
                    div {
                        +APPS_OVERVIEW_TABLE_ROW_HEADER_ICON
                        attrs {
                            style = kotlinext.js.js {
                                textAlign = "center"
                            }

                        }
                    }
                }
                Col {

                    div {
                        +APPS_OVERVIEW_TABLE_ROW_HEADER_NAME
                        attrs {
                            style = kotlinext.js.js {
                                textAlign = "center"
                            }

                        }
                    }
                }
                Col {

                    div {
                        +APPS_OVERVIEW_TABLE_ROW_HEADER_DOWNLOAD
                        attrs {
                            style = kotlinext.js.js {
                                textAlign = "center"
                            }

                        }
                    }
                }
                Col {

                    div {
                        +APPS_OVERVIEW_TABLE_ROW_HEADER_UNINSTALL
                        attrs {
                            style = kotlinext.js.js {
                                textAlign = "center"
                            }

                        }
                    }
                }

            }

            for (item in state.appsResult) {

                Row {
                    Col {
                        initAppIconView()
                    }
                    Col {

                        div {
                            +item.name.toString()
                            attrs {
                                style = kotlinext.js.js {
                                    textAlign = "center"
                                }

                            }
                        }
                    }
                    Col {
                        initDownloadButton(DownloadIcon, item)
                    }
                    Col {
                        initDeleteButton(DeleteIcon, item)
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

    private fun RElementBuilder<ColProps>.initAppIconView() {
        div {
            img {
                attrs {
                    src = "./img/ic_launcher.png"
                    style = kotlinext.js.js {
                        display = "block"
                        margin = 0
                    }

                }
            }
        }
    }

    private fun RElementBuilder<ColProps>.initDownloadButton(DownloadIcon: RClass<dynamic>, item: AppFile) {
        div {
            DownloadIcon {

            }
            attrs {
                onClickFunction = {
                    window.location.href =
                            NetworkUtil.appDownloadUrl(item.packageName ?: "")
                }
                style = kotlinext.js.js {
                    textAlign = "center"
                }


            }
        }
    }

    private fun RElementBuilder<ColProps>.initDeleteButton(DeleteIcon: RClass<dynamic>, item: AppFile) {
        div {
            DeleteIcon {

            }
            attrs {
                onClickFunction = {
                    window.location.href =
                            NetworkUtil.appDownloadUrl(item.packageName ?: "")
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