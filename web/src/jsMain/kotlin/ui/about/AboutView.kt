package ui.about


import network.NetworkUtil
import react.RBuilder
import react.RComponent
import react.RProps
import react.RState
import react.dom.a
import react.dom.div
import ui.toolbar

interface AboutState : RState {
    var open: Boolean
}


class AboutView : RComponent<RProps, AboutState>() {


    override fun RBuilder.render() {
        toolbar()

        div {
            +"Sheasy v.0.0.1"
        }
        div() {
            a {
                +"GIT PAGE"
                attrs {
                    href = NetworkUtil.repoSite
                    target = "_blank"
                }
            }


        }

    }
}

fun RBuilder.about() = child(AboutView::class) {}


