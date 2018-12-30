package de.jensklingenberg.react.ui.screenshare


import react.*
import react.dom.div
import react.dom.img
import de.jensklingenberg.react.ui.common.toolbar

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


