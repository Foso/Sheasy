package ui.media


import components.materialui.icons.PauseIcon
import components.materialui.icons.PlayArrowIcon
import components.materialui.icons.SkipNextIcon
import components.materialui.icons.SkipPreviousIcon
import react.RBuilder
import react.RComponent
import react.RProps
import react.RState


class MediaView : RComponent<RProps, RState>() {

    override fun RBuilder.render() {
        SkipPreviousIcon {}
        PauseIcon {}
        PlayArrowIcon {}
        SkipNextIcon {}
    }

}

fun RBuilder.MediaView() = child(MediaView::class) {}