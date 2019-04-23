package components.reactstrap

import react.RClass
import react.RProps

var FormGroup: RClass<FormGroupProps> = ReactStrapImport.FormGroup


external interface FormGroupProps : RProps {
    var row: Boolean? get() = definedExternally; set(value) = definedExternally
    var check: Boolean? get() = definedExternally; set(value) = definedExternally
    var disabled: Boolean? get() = definedExternally; set(value) = definedExternally
    var className: String? get() = definedExternally; set(value) = definedExternally

}
