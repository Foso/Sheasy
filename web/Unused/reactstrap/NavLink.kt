package components.reactstrap

import org.w3c.dom.events.Event
import react.RClass
import react.RProps

var NavLink: RClass<NavLinkProps> = ReactStrapImport.NavLink


external interface NavLinkProps : RProps {
    var onClick: (Event) -> Unit
}


