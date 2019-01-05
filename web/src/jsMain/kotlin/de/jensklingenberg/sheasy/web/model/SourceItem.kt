package de.jensklingenberg.sheasy.web.model

import de.jensklingenberg.sheasy.web.ui.common.ReactView
import react.*




abstract class SourceItem : ReactView {
    abstract override fun render(rBuilder: RBuilder)
}


inline fun List<ReactView>.render(rBuilder: RBuilder) {
    this.forEach {
        with(rBuilder) {
            it.render(this)
        }
    }
}

