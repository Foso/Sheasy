package de.jensklingenberg.sheasy.network

import de.jensklingenberg.sheasy.model.Event

interface ApiEventListener {
    fun onShare(test: Event)
}