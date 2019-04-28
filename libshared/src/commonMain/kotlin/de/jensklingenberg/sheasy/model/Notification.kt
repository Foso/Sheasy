package de.jensklingenberg.sheasy.model

data class Notification(
    val packageName: String?,
    val title: String?,
    val text: String?,
    val subText: String?,
    val postTime: String?
)