package de.jensklingenberg.sheasy.model

enum class EventCategory(val title: String) {
    REQUEST("Request"), DEFAULT(""), CONNECTION("Connection"), MEDIA("Media"), SERVER("Server")
}