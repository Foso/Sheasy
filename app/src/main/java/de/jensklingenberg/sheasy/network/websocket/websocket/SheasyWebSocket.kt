package de.jensklingenberg.sheasy.network.websocket.websocket

import de.jensklingenberg.sheasy.model.ShareItem

interface SheasyWebSocket {
    fun isOpen(): Boolean

    fun send(shareItem: ShareItem)
    fun send(payload: ByteArray?)
}