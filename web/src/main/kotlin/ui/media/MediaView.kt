package ui.media


import components.materialui.icons.PauseIcon
import components.materialui.icons.PlayArrowIcon
import components.materialui.icons.SkipNextIcon
import components.materialui.icons.SkipPreviousIcon
import react.*


interface vNotificationVState : RState {

}


class MediaView : RComponent<RProps, vNotificationVState>() {


    override fun vNotificationVState.init(props: RProps) {

    }


    private fun handleChange() {
        setState {

        }

    }


    override fun RBuilder.render() {
        SkipPreviousIcon {}
        PauseIcon {}
        PlayArrowIcon {}
        SkipNextIcon {}
    }

}

fun RBuilder.MediaView() = child(MediaView::class) {

}