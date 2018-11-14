package components


import react.RClass
import react.RProps

external interface Settings {
    var preserveAspectRatio: Boolean? get() = definedExternally; set(value) = definedExternally
    var context: Any? get() = definedExternally; set(value) = definedExternally
    var scaleMode: Any? get() = definedExternally; set(value) = definedExternally
    var clearCanvas: Boolean? get() = definedExternally; set(value) = definedExternally
    var progressiveLoad: Boolean? get() = definedExternally; set(value) = definedExternally
    var hideOnTransparent: Boolean? get() = definedExternally; set(value) = definedExternally
    var className: String? get() = definedExternally; set(value) = definedExternally
}

external interface Options {
    var loop: Boolean? get() = definedExternally; set(value) = definedExternally
    var autoplay: Boolean? get() = definedExternally; set(value) = definedExternally
    var animationData: Any
    var rendererSettings: Settings? get() = definedExternally; set(value) = definedExternally
}

external interface EventListener {
    var eventName: dynamic /* String /* "complete" */ | String /* "loopComplete" */ | String /* "enterFrame" */ | String /* "segmentStart" */ | String /* "config_ready" */ | String /* "data_ready" */ | String /* "loaded_images" */ | String /* "DOMLoaded" */ | String /* "destroy" */ */
    var callback: () -> Unit
}

external interface LottieProps : RProps {
    var options: Options
    var height: dynamic /* String | Number */ get() = definedExternally; set(value) = definedExternally
    var width: dynamic /* String | Number */ get() = definedExternally; set(value) = definedExternally
    var isStopped: Boolean? get() = definedExternally; set(value) = definedExternally
    var isPaused: Boolean? get() = definedExternally; set(value) = definedExternally
    var eventListeners: Array<EventListener>? get() = definedExternally; set(value) = definedExternally
    var segments: Array<Number>? get() = definedExternally; set(value) = definedExternally
    var speed: Number? get() = definedExternally; set(value) = definedExternally
    var direction: Number? get() = definedExternally; set(value) = definedExternally
    var ariaRole: dynamic /* String | String /* "button" */ */ get() = definedExternally; set(value) = definedExternally
    var ariaLabel: dynamic /* String | String /* "animation" */ */ get() = definedExternally; set(value) = definedExternally
    var isClickToPauseDisabled: Boolean? get() = definedExternally; set(value) = definedExternally
    var title: String? get() = definedExternally; set(value) = definedExternally
}

@JsModule("react-lottie/dist")
@JsName("default")
external val LottieImport: dynamic

var Lottie: RClass<LottieProps> = LottieImport.default
