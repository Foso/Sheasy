package de.jensklingenberg.sheasy.web.ui.media


import components.materialui.icons.PauseIcon
import components.materialui.icons.PlayArrowIcon
import components.materialui.icons.SkipNextIcon
import components.materialui.icons.SkipPreviousIcon
import de.jensklingenberg.sheasy.web.ui.common.styleProps
import react.*
import react.dom.p

interface MediaViewProps : RProps {
    var show: Boolean
}

interface MediaViewState : RState {
    var show: Boolean

}

class MediaView : RComponent<MediaViewProps, MediaViewState>() {

    /****************************************** React Lifecycle methods  */

    override fun MediaViewState.init(props: MediaViewProps) {
        setState {
            show = true
        }
    }


    override fun RBuilder.render() {
        p {
            attrs {
                styleProps(display = checkVisibility(this@MediaView.state.show))
            }
            SkipPreviousIcon {}
            PauseIcon {}
            PlayArrowIcon {}
            SkipNextIcon {}

        }

    }

    /****************************************** Presenter methods  */


    fun checkVisibility(show: Boolean): String {
        return if (show) ""
        else "none"
    }
}

fun RBuilder.MediaView(visible: Boolean = true) = child(MediaView::class) {
    attrs.show = visible


}