package ui.screenshare


import model.AppFile
import network.NetworkUtil
import react.*
import react.dom.a
import react.dom.div
import react.dom.img
import ui.apps.AppsPresenter
import ui.toolbar

interface AboutState : RState {
    var open: Boolean
    var errorMessage: String

}


class ScreenShareView : RComponent<RProps, AboutState>(), ScreenshareContract.View {
    private var presenter: ScreensharePresenter? = null


    override fun componentDidMount() {
        presenter = ScreensharePresenter(this)
        presenter?.componentDidMount()

    }


    override fun setData(apps: String) {
        setState {
            errorMessage = apps
        }

    }


    override fun RBuilder.render() {
        toolbar()

        div {
            +"Sheasy v.0.0.1"
        }
        div() {

            img {
                attrs {
                    src = state.errorMessage
                }
            }


        }

    }
}

fun RBuilder.screenshare() = child(ScreenShareView::class) {}


