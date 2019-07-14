package de.jensklingenberg.sheasy.web.ui.common

import de.jensklingenberg.sheasy.web.model.SourceItem
import react.RBuilder
import react.dom.div
import react.dom.hr


class GenericListHeaderSourceItem(val linkItem: StringSourceItem) : SourceItem() {

    override fun render(rBuilder: RBuilder) {
        with(rBuilder) {
            div("List-Header") {
                +linkItem.title

            }
            hr { }
        }
    }

}