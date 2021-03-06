@file:Suppress(
    "INTERFACE_WITH_SUPERCLASS",
    "OVERRIDING_FINAL_MEMBER",
    "RETURN_TYPE_MISMATCH_ON_OVERRIDE",
    "CONFLICTING_OVERLOADS",
    "EXTERNAL_DELEGATION",
    "NESTED_CLASS_IN_EXTERNAL_INTERFACE"
)

package components.materialui

import react.RClass

@JsModule("@material-ui/core/ExpansionPanel/ExpansionPanel")
external val ExpansionPanelImport: dynamic

external interface ExpansionPanelProps : PaperProps {
    var CollapseProps: Any? get() = definedExternally; set(value) = definedExternally
    var defaultExpanded: Boolean? get() = definedExternally; set(value) = definedExternally
    var onChange: EventHandlerFunction? get() = definedExternally; set(value) = definedExternally
    var expanded: Boolean?
}

var ExpansionPanel: RClass<ExpansionPanelProps> = ExpansionPanelImport.default



