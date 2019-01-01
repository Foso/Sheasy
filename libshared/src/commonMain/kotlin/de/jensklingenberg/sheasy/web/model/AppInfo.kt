package de.jensklingenberg.sheasy.web.model


data class AppInfo(
    val sourceDir: String,
    val icon: String?,
    val name: String,
    val packageName: String,
    val installTime: String
)

