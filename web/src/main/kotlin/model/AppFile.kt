package model


data class AppFile(val name: String, val packageName: String, val installTime: String)

data class NotificationResponse(
    val packageName: String?,
    val title: String?,
    val text: String?,
    val subText: String?,
    val postTime: Long
)
