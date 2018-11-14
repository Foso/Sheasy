package de.jensklingenberg.sheasy.model

import android.graphics.drawable.Drawable

data class AppsResponse(
    val name: String,
    val packageName: String,
    val installTime: String,
    val icon: Drawable
)
