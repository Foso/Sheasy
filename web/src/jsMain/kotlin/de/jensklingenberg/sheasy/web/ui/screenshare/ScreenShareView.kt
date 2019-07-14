package de.jensklingenberg.sheasy.web.ui.screenshare


import de.jensklingenberg.sheasy.web.ui.common.toolbar
import react.*
import react.dom.div
import react.dom.img

interface AboutState : RState {
    var open: Boolean
    var errorMessage: String

}


class ScreenShareView : RComponent<RProps, AboutState>(), ScreenshareContract.View {
    private var presenter: ScreenshareContract.Presenter? = null


    override fun componentDidMount() {
        presenter = ScreensharePresenter(this)
        presenter?.componentDidMount()

    }


    override fun setData(base64: String) {
        setState {
            errorMessage = base64
        }

    }

    override fun RBuilder.render() {
        toolbar()

        div {
            +"Sheasy v.0.0.1"
        }
        div {

            img {
                attrs {
                    src = state.errorMessage
                }
            }


        }

    }
}

fun RBuilder.screenshare() = child(ScreenShareView::class) {}


