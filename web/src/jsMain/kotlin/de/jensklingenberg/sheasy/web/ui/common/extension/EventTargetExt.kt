package de.jensklingenberg.sheasy.web.ui.common.extension

import de.jensklingenberg.sheasy.web.model.File
import org.w3c.dom.events.EventTarget


val EventTarget?.selectedFile: File?
    get() {
        return this.asDynamic().files[0]
    }