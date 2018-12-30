package de.jensklingenberg.react.ui.common

import kotlinx.html.CommonAttributeGroupFacade
import kotlinx.html.style


inline fun LayoutProps.styleProps(textAlign: String = "", display: String = "", width: String = "") {
    style = de.jensklingenberg.react.ui.common.styleProps(textAlign = textAlign, display = display, width = width)

}

inline fun CommonAttributeGroupFacade.styleProps(textAlign: String = "", display: String = "", width: String = "") {
    style = de.jensklingenberg.react.ui.common.styleProps(textAlign = textAlign, display = display, width = width)

}

fun styleProps(textAlign: String = "", display: String = "", width: String = ""): String {
    return kotlinext.js.js {
        if (textAlign.isNotEmpty()) {
            this.textAlign = textAlign
        }

        if (width.isNotEmpty()) {
            this.width = width
        }

        if (display.isNotEmpty()) {
            this.display = display
        }
    }

}


external interface LayoutProps {
    var style: dynamic get() = definedExternally; set(value) = definedExternally

}