package de.jensklingenberg.sheasy.web.data

import de.jensklingenberg.sheasy.web.model.Foo

interface ReactView {
    fun render(rBuilder: Foo)
}