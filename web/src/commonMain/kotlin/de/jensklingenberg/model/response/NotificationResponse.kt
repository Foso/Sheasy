package de.jensklingenberg.model.response


data class NotificationResponse(
    val packageName: String?,
    val title: String?,
    val text: String?,
    val subText: String?,
    val postTime: Long
)
