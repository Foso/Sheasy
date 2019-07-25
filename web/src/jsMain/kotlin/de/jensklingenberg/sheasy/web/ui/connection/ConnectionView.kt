package de.jensklingenberg.sheasy.web.ui.connection

import de.jensklingenberg.sheasy.web.model.SourceItem
import de.jensklingenberg.sheasy.web.ui.common.styleProps
import react.*
import react.dom.div
import react.dom.h1
import react.dom.img
import react.dom.p


interface ConnectionViewState : RState {
    var item: List<SourceItem>

}

class ConnectionView : RComponent<RProps, ConnectionViewState>(), ConnectionContract.View {


    var presenter = ConnectionPresenter(this)


    override fun ConnectionViewState.init() {
        item = emptyList()

    }

    override fun componentDidMount() {
        presenter.componentDidMount()
    }

    override fun setData(items: List<SourceItem>) {
        setState {
            this.item = items
        }
    }


    override fun RBuilder.render() {
        //toolbar()

        // state.item.render(this)

        div {
            h1 {
                +"Sheasy"
            }

            p {
                +"Please accept the connection in the notification bar on your device and reload the page."
            }

            img {
                attrs {
                    src = "http://192.168.178.20:8766/web/connection/device.png"
                    width = "30%"
                    height = "50%"
                }
            }

            attrs {
                styleProps(textAlign = "center")

            }
        }

    }
}


fun RBuilder.connection() = child(ConnectionView::class) {}




