package components.reactstrap

import org.w3c.dom.events.Event
import react.RClass
import react.RProps


var Alert: RClass<AlertsProps> = ReactStrapImport.Alert


external interface AlertsProps : RProps {
    var color: String? get() = definedExternally; set(value) = definedExternally
    var toggle: (Event) -> Unit

    var isOpen: Boolean? get() = definedExternally; set(value) = definedExternally
}

