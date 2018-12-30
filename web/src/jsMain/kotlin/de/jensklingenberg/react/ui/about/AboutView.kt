package de.jensklingenberg.react.ui.about


import components.listview.ListView
import components.listview.SourceItem
import components.listview.listView
import components.listview.rend
import kotlinx.html.js.onClickFunction
import components.network.ApiEndPoint
import de.jensklingenberg.model.Error
import de.jensklingenberg.model.response.Status
import react.dom.a
import react.dom.button
import react.dom.div
import de.jensklingenberg.react.ui.common.toolbar
import react.*
import kotlin.browser.window

interface AboutState : RState {
    var itemsList: List<SourceItem>
    var status: Status
}


class AboutView : RComponent<RProps, AboutState>(), AboutContract.View {
    private val presenter: AboutPresenter = AboutPresenter(this)


    override fun AboutState.init() {
        itemsList = emptyList()
    }

    override fun setData(items: List<SourceItem>) {
        setState {
            status = Status.SUCCESS
            itemsList = items
        }

    }

    override fun componentDidMount() {
        presenter.componentDidMount()
    }

    override fun showError(error: Error) {

    }


    override fun RBuilder.render() {
        toolbar()






        state.itemsList.forEach {
            rend(it)
        }




        button {
            attrs {
                text("Download Sheasy Apk")
                onClickFunction = {
                    window.location.href =
                        ApiEndPoint.appDownloadUrl("de.jensklingenberg.sheasy")
                }
            }
        }
        div {
            a {
                +"GIT PAGE"
                attrs {
                    href = ApiEndPoint.repoSite
                    target = "_blank"
                }
            }


        }

    }
}

fun RBuilder.about() = child(AboutView::class) {}


