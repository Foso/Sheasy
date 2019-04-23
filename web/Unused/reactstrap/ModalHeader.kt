package components.reactstrap

import org.w3c.dom.events.Event
import react.RClass
import react.RProps


var ModalHeader: RClass<ModalHeaderProps> = ReactStrapImport.ModalHeader

external interface ModalHeaderProps : RProps {
    var toggle: (Event) -> Unit

}


