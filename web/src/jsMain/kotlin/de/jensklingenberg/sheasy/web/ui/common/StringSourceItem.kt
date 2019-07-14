package de.jensklingenberg.sheasy.web.ui.common

import de.jensklingenberg.sheasy.web.model.SourceItem
import react.RBuilder
import react.dom.hr
import react.dom.p

class StringSourceItem(val title: String) : SourceItem() {

    override fun render(rBuilder: RBuilder) {
        with(rBuilder) {
            p {
                +title
            }
            hr { }
        }
    }
}