package reactwm

import react.RClass
import react.RProps


//@JsModule("reactwm/pkg/models/manager")
//@JsName("default")
external val managerImport: dynamic

var manager: RClass<managerProps> = managerImport.Manager

interface managerProps : RProps {
    fun open(id: Int, test: dynamic, tt: dynamic)
}