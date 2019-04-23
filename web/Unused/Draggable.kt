package de.jensklingenberg.sheasy.web.components

import org.w3c.dom.HTMLElement
import org.w3c.dom.events.MouseEvent
import react.*
import react.dom.div


@JsModule("react-draggable")
@JsNonModule
@JsName("default")
external val Draggable: RClass<DraggableProps>


external interface DraggableBounds {
    var left: Number
    var right: Number
    var top: Number
    var bottom: Number
}


external interface DraggableProps : DraggableCoreProps {
    var axis: dynamic /* String /* "both" */ | String /* "x" */ | String /* "y" */ | String /* "none" */ */
    var bounds: dynamic /* String | Boolean | drag.DraggableBounds */
    var defaultClassName: String
    var defaultClassNameDragging: String
    var defaultClassNameDragged: String
    var defaultPosition: dynamic
    var position: ControlPosition
    var initialText: String

}

external interface DraggableData {
    var node: HTMLElement
    var x: Number
    var y: Number
    var deltaX: Number
    var deltaY: Number
    var lastX: Number
    var lastY: Number
}

external interface ControlPosition {
    var x: Number
    var y: Number
}

external interface DraggableCoreProps : RProps {
    var allowAnyClick: Boolean
    var cancel: String
    var disabled: Boolean
    var enableUserSelectHack: Boolean
    var offsetParent: HTMLElement
    var grid: dynamic /* JsTuple<Number, Number> */
    var handle: String
    var onStart: (e: MouseEvent, data: DraggableData) -> dynamic /* Boolean | Unit */
    var onDrag: (e: MouseEvent, data: DraggableData) -> dynamic /* Boolean | Unit */
    var onStop: (e: MouseEvent, data: DraggableData) -> dynamic /* Boolean | Unit */
    var onMouseDown: (e: MouseEvent) -> Unit
}


interface aaad : RState {
    var text: String
}


class SearchBar(props: DraggableProps) : RComponent<DraggableProps, aaad>(props) {

    override fun aaad.init(props: DraggableProps) {
        text = props.initialText

    }

    override fun RBuilder.render() {

        Draggable {
            div("react-draggable") {
                attrs.text("ddd")


            }

        }
    }


    private fun handleChange(value: String) {
        setState {
            text = value
        }
        console.log(value)
    }


}


fun RBuilder.searchBar(onSearchTermChange: String) = child(SearchBar::class) {
    attrs.initialText = onSearchTermChange
}