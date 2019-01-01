package de.jensklingenberg.sheasy.web.components.listview

import de.jensklingenberg.sheasy.web.model.SourceItem
import react.RBuilder
import react.dom.div

class StringSourceItem(val title:String) : SourceItem() {

    override fun render(rBuilder: RBuilder) {
        with(rBuilder){
            div {
                +title
            }
        }
    }
}