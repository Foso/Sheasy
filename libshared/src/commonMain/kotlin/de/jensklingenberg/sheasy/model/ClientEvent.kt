package de.jensklingenberg.sheasy.model

open class ClientEvent(open var message: String = "")

data class RefreshClientEvent(override var message: String = "Refresh") : ClientEvent(message)