package de.jensklingenberg.sheasy.web.model.response


data class NotificationResponse(
    val packageName: String?,
    val title: String?,
    val text: String?,
    val subText: String?,
    val postTime: Long
)
