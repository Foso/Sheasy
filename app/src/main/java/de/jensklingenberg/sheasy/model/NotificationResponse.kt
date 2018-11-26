package de.jensklingenberg.sheasy.model

data class NotificationResponse(
    val packageName: String?,
    val title: String?,
    val text: String?,
    val subText: String?,
    val postTime: Long
)