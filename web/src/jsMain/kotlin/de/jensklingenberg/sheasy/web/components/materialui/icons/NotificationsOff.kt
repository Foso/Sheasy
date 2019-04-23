
@file:Suppress(
    "INTERFACE_WITH_SUPERCLASS",
    "OVERRIDING_FINAL_MEMBER",
    "RETURN_TYPE_MISMATCH_ON_OVERRIDE",
    "CONFLICTING_OVERLOADS",
    "EXTERNAL_DELEGATION",
    "NESTED_CLASS_IN_EXTERNAL_INTERFACE"
)

package de.jensklingenberg.sheasy.web.components.materialui.icons

import components.materialui.ButtonProps
import react.RClass

@JsModule("@material-ui/icons/NotificationsOff")
external val NotificationsOffIconImport: dynamic

external interface NotificationsOffIconProps : ButtonProps {
}

var NotificationsOffIcon: RClass<NotificationsOffIconProps> = NotificationsOffIconImport.default
