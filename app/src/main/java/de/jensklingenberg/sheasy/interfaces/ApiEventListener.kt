package de.jensklingenberg.sheasy.interfaces

import de.jensklingenberg.sheasy.model.Event

interface ApiEventListener {
    fun onShare(test: Event)
}