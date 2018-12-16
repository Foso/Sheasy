package components

import org.w3c.dom.events.Event
import react.RClass
import react.RProps

@JsModule("react-dropzone")
@JsName("default")
external val dropzoneImport: dynamic

var Dropzone: RClass<DropzoneProps> = dropzoneImport.default

external interface ImageFile {
    var preview: String? get() = definedExternally; set(value) = definedExternally
}

external interface DropzoneProps : RProps {
    var accept: dynamic /* String | ReadonlyArray<String> */ get() = definedExternally; set(value) = definedExternally
    var disableClick: Boolean? get() = definedExternally; set(value) = definedExternally
    var disabled: Boolean? get() = definedExternally; set(value) = definedExternally
    var disablePreview: Boolean? get() = definedExternally; set(value) = definedExternally
    var preventDropOnDocument: Boolean? get() = definedExternally; set(value) = definedExternally
    var inputProps: dynamic get() = definedExternally; set(value) = definedExternally
    var maxSize: Number? get() = definedExternally; set(value) = definedExternally
    var minSize: Number? get() = definedExternally; set(value) = definedExternally
    var activeClassName: String? get() = definedExternally; set(value) = definedExternally
    var acceptClassName: String? get() = definedExternally; set(value) = definedExternally
    var rejectClassName: String? get() = definedExternally; set(value) = definedExternally
    var disabledClassName: String? get() = definedExternally; set(value) = definedExternally
    var activeStyle: dynamic get() = definedExternally; set(value) = definedExternally
    var acceptStyle: dynamic get() = definedExternally; set(value) = definedExternally
    var rejectStyle: dynamic get() = definedExternally; set(value) = definedExternally
    var disabledStyle: dynamic get() = definedExternally; set(value) = definedExternally
    var onDrop: ((accepted: Array<ImageFile>, rejected: Array<ImageFile>, event: (Event) -> Unit) -> Unit)? get() = definedExternally; set(value) = definedExternally
    var onDropAccepted: ((acceptedOrRejected: Array<ImageFile>, event: (Event) -> Unit) -> Unit)? get() = definedExternally; set(value) = definedExternally
    var onDropRejected: ((acceptedOrRejected: Array<ImageFile>, event: (Event) -> Unit) -> Unit)? get() = definedExternally; set(value) = definedExternally
    var onFileDialogCancel: (() -> Unit)? get() = definedExternally; set(value) = definedExternally
}