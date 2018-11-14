package components.reactstrap

import react.RClass
import react.RProps


var Col: RClass<ColProps> = ReactStrapImport.Col


external interface ColProps : RProps {
    var sm: String? get() = definedExternally; set(value) = definedExternally

}
