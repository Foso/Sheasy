package network

import react.RClass
import react.RProps


@JsModule("simple-websocket")
external val SocketImport: dynamic

var Socket: RClass<SocketOptions> = SocketImport


external interface SocketOptions : RProps {
    var url: String? get() = definedExternally; set(value) = definedExternally
}
