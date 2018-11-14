package components.reactstrap

import org.w3c.dom.events.Event
import react.RClass
import react.RProps


class ButtonColor() {
    companion object {
        val Primary = "primary"
        val Danger = "danger"
        val Succes = "success"
        val Info = "info"
        val Secondary = "secondary"
        val Link = "link"
        val Warning = "warning"
    }
}

var Button: RClass<ButtonProps> = ReactStrapImport.Button


external interface ButtonProps : RProps {
    var color: String? get() = definedExternally; set(value) = definedExternally
    var onClick: (Event) -> Unit
    var id: String? get() = definedExternally; set(value) = definedExternally

}


