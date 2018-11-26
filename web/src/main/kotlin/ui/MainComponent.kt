package ui


import components.Draggable
import react.RBuilder
import react.RComponent
import react.RProps
import react.RState
import react.dom.div

interface AppState : RState {
    var open: Boolean
}


class App : RComponent<RProps, AppState>() {


    override fun RBuilder.render() {
        toolbar()

        Draggable {
            div("react-draggable") {
                +"DRAGi"


            }
        }
    }
}

fun RBuilder.app() = child(App::class) {}


