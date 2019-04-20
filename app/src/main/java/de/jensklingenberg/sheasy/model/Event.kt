package de.jensklingenberg.sheasy.model

enum class MessageType {
    INCOMING, OUTGOING
}

sealed class Event(var category: EventCategory, open var text: String, open var time: String = "")

class MessageEvent(override var text: String, override var time: String = "", val type: MessageType) :
    Event(EventCategory.MESSAGE, text, time)

class ConnectionEvent(override var text: String, override var time: String = "") :
    Event(EventCategory.CONNECTION, text, time)