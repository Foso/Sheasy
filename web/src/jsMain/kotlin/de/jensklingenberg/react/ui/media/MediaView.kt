package de.jensklingenberg.react.ui.media


import components.materialui.icons.PauseIcon
import components.materialui.icons.PlayArrowIcon
import components.materialui.icons.SkipNextIcon
import components.materialui.icons.SkipPreviousIcon
import react.*
import react.dom.div
import react.dom.p
import de.jensklingenberg.react.ui.common.ToolbarState
import de.jensklingenberg.react.ui.common.styleProps

interface MediaViewProps : RProps {
    var show: Boolean
}

interface MediaViewState : RState {
    var show: Boolean

}

class MediaView : RComponent<MediaViewProps, MediaViewState>() {

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


    fun checkVisibility(show: Boolean): String {
        return if (show) ""
        else "none"
    }
}

fun RBuilder.MediaView(visible: Boolean = true) = child(MediaView::class) {
    attrs.show = visible


}