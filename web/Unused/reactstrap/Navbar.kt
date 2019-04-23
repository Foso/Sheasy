package components.reactstrap

import react.RClass
import react.RProps


var Navbar: RClass<NavbarProps> = ReactStrapImport.Navbar


external interface NavbarProps : RProps {
    var light: Boolean? get() = definedExternally; set(value) = definedExternally
    var dark: Boolean? get() = definedExternally; set(value) = definedExternally
    var inverse: Boolean? get() = definedExternally; set(value) = definedExternally
    var full: Boolean? get() = definedExternally; set(value) = definedExternally
    var fixed: String? get() = definedExternally; set(value) = definedExternally
    var sticky: String? get() = definedExternally; set(value) = definedExternally
    var color: String? get() = definedExternally; set(value) = definedExternally
    var role: String? get() = definedExternally; set(value) = definedExternally
    var tag: dynamic
    var className: String? get() = definedExternally; set(value) = definedExternally
    var cssModule: dynamic
    var toggleable: Boolean? get() = definedExternally; set(value) = definedExternally
    var expand: dynamic


}


