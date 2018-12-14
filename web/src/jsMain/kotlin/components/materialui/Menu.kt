@file:Suppress("INTERFACE_WITH_SUPERCLASS", "OVERRIDING_FINAL_MEMBER", "RETURN_TYPE_MISMATCH_ON_OVERRIDE", "CONFLICTING_OVERLOADS", "EXTERNAL_DELEGATION", "NESTED_CLASS_IN_EXTERNAL_INTERFACE")

package components.materialui

import react.RClass
import react.materialui.ListItemProps

@JsModule("@material-ui/core/Menu/Menu")
external val MenuImport: dynamic

external interface MenuProps : ListItemProps {

}

var Menu: RClass<MenuItemProps> = MenuImport.default
