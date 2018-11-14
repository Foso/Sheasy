package components.reactstrap

import org.w3c.dom.events.Event
import react.RClass
import react.RProps


var Input: RClass<InputProps> = ReactStrapImport.Input


external interface InputProps : RProps {

    var type: String? get() = definedExternally; set(value) = definedExternally
    var name: String? get() = definedExternally; set(value) = definedExternally
    var id: String? get() = definedExternally; set(value) = definedExternally
    var placeholder: String? get() = definedExternally; set(value) = definedExternally
    var onChange: (Event) -> Unit


}
