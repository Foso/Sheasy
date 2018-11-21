package de.jensklingenberg.sheasy.model

import android.graphics.drawable.Drawable

data class AppInfo(
    val sourceDir: String,
    val icon: Drawable,
    val name: String,
    val packageName: String,
    val installTime: String
)

data class AppResponse(val name: String, val packageName: String, val installTime: String)
