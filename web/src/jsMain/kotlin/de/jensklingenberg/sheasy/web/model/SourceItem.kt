package de.jensklingenberg.sheasy.web.model

import de.jensklingenberg.sheasy.web.ui.common.ReactView
import react.RBuilder


abstract class SourceItem : ReactView {
    abstract override fun render(rBuilder: RBuilder)
}


inline fun List<ReactView>.render(rBuilder: RBuilder) {


    this@render.forEach {
        with(rBuilder) {
            it.render(this)
        }

    }

}

