package de.jensklingenberg.sheasy.web.ui.common

import de.jensklingenberg.sheasy.web.model.Foo

interface ReactView {
    fun render(rBuilder: Foo)
}