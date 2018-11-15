@file:Suppress("INTERFACE_WITH_SUPERCLASS", "OVERRIDING_FINAL_MEMBER", "RETURN_TYPE_MISMATCH_ON_OVERRIDE", "CONFLICTING_OVERLOADS", "EXTERNAL_DELEGATION", "NESTED_CLASS_IN_EXTERNAL_INTERFACE")

package react.materialui

import react.RClass
import react.RProps
import react.ReactElement

@JsModule("@material-ui/core/ListItemIcon/ListItemIcon")
external val ListItemIconImport: dynamic

external interface ListItemIconProps : RProps {
    var children: ReactElement
}

var ListItemIcon: RClass<ListItemIconProps> = ListItemIconImport.default