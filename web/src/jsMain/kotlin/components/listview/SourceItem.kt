package components.listview

import react.*
import react.dom.div



abstract class SourceItem  {


   abstract fun tterror(rBuilder: RBuilder)



}

fun RBuilder.rend(sourceItem: SourceItem){
    sourceItem.tterror(this)
}
