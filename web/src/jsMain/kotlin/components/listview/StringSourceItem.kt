package components.listview

import react.RBuilder
import react.RProps
import react.dom.div

class StringSourceItem(val title:String) : SourceItem() {

    override fun tterror(rBuilder: RBuilder) {
        with(rBuilder){
            div {
                +title
            }
        }

    }

}