package components.reactstrap

import react.RClass
import react.RProps

var Modal: RClass<ModalProps> = ReactStrapImport.Modal

external interface ModalProps : RProps {
    var isOpen: Boolean
    var size: String
}


