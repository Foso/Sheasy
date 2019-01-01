package components.reactstrap

import react.RClass
import react.RProps


var Nav: RClass<NavProps> = ReactStrapImport.Nav

external interface NavProps : RProps {
    var className: String
}

class NavsClassName() {
    companion object {
        val ML_AUTO = "ml-auto"
    }
}

enum class NavClassName(val value: String) {
    ML_AUTO("ml-auto")
}
