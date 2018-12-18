package ui.common

import kotlinx.html.CommonAttributeGroupFacade
import kotlinx.html.style


inline fun LayoutProps.styleProps(textAlign:String) {
    style = kotlinext.js.js {
        this.textAlign = textAlign
    }

}

fun CommonAttributeGroupFacade.styleProps(textAlign:String){
    style = kotlinext.js.js {
        this.textAlign = textAlign
    }
}

external interface LayoutProps{
    var style:dynamic get() = definedExternally; set(value) = definedExternally

}