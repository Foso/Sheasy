package de.jensklingenberg.sheasy.web.ui.common.extension

import org.w3c.dom.events.EventTarget
import org.w3c.files.File


val EventTarget?.selectedFile: File?
    get() {
        return this.asDynamic().files[0]
    }