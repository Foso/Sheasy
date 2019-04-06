package de.jensklingenberg.sheasy.web.ui.common

import de.jensklingenberg.sheasy.web.model.LinkItem
import de.jensklingenberg.sheasy.web.model.SourceItem
import react.RBuilder
import react.dom.a
import react.dom.div


class LinkSourceItem(val linkItem: LinkItem) : SourceItem() {

    override fun render(rBuilder: RBuilder) {
        with(rBuilder){
            div {
                a {
                    +linkItem.title
                    attrs {
                        href = linkItem.href
                        target = linkItem.target
                    }
                }
            }
        }
    }

}