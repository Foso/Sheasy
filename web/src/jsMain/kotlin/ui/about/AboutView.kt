package ui.about


import kotlinx.html.js.onClickFunction
import network.NetworkUtil
import react.RBuilder
import react.RComponent
import react.RProps
import react.RState
import react.dom.a
import react.dom.button
import react.dom.div
import ui.common.toolbar
import kotlin.browser.window

interface AboutState : RState {
    var open: Boolean
}


class AboutView : RComponent<RProps, AboutState>() {


    override fun RBuilder.render() {
        toolbar()

        div {
            +"Sheasy v.0.0.1"
        }
        button {
            attrs {
                text("Download Sheasy Apk")
                onClickFunction = {
                    window.location.href =
                            NetworkUtil.appDownloadUrl("de.jensklingenberg.sheasy")
                }
            }
        }
        div {
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


