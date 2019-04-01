package de.jensklingenberg.sheasy.model


data class Event(val category: EventCategory, val text: String, var time:String = "")