package de.jensklingenberg.sheasy.web.components.reactstrap

import components.reactstrap.ReactStrapImport
import org.w3c.dom.events.Event
import react.RClass
import react.RProps

/**
 * https://components.reactstrap.github.io/components/tooltips/
 */
var Tooltip: RClass<TooltipProps> = ReactStrapImport.Tooltip

external interface UncontrolledTooltipProps : RProps {
    var placement: String? get() = definedExternally; set(value) = definedExternally
    var target: String? get() = definedExternally; set(value) = definedExternally

}

external interface TooltipProps : UncontrolledTooltipProps {
    var toggle: (Event) -> Unit
    var isOpen: Boolean? get() = definedExternally; set(value) = definedExternally

}


