package de.jensklingenberg.sheasy.web.ui.common

import react.RBuilder
import react.RComponent
import react.RProps
import react.RState

open class BaseComponent<P:RProps,S:RState> : RComponent<P,S>(){

    var rBuilder : RBuilder?=null


    override fun RBuilder.render() {
        rBuilder=this
    }


}