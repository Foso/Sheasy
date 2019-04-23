package de.jensklingenberg.sheasy.web.components.Notification

import org.w3c.dom.events.Event
import react.RClass
import react.RProps
import de.jensklingenberg.sheasy.web.components.webapi.NotificationOptions


@JsModule("react-web-notification")
external val NotificationImport: dynamic

var Notification: RClass<NotificationProps> = NotificationImport.default


external interface NotificationProps : RProps {
    var title: String? get() = definedExternally; set(value) = definedExternally
    var timeout: Int? get() = definedExternally; set(value) = definedExternally
    var ignore: Boolean? get() = definedExternally; set(value) = definedExternally
    var disableActiveWindow: Boolean? get() = definedExternally; set(value) = definedExternally

    var notSupported: (Event) -> Unit
    var onPermissionGranted: (Event) -> Unit
    var onPermissionDenied: (Event) -> Unit
    var onShow: (Event) -> Unit
    var onClick: (Event) -> Unit
    var onClose: (Event) -> Unit
    var onError: (Event) -> Unit
    var options: NotificationOptions

}

interface ReactNotificationOptions : NotificationOptions {

}


var defaultReactNotificationOptions = object : ReactNotificationOptions {
    override var tag: String? = ""
    override var icon: String? = ""
    override var body: String? = ""
    override var title: String? = ""


}