package de.jensklingenberg.sheasy.model

import android.graphics.drawable.Drawable

data class AndroidAppInfo(
    override val sourceDir: String,
    override val name: String,
    override val packageName: String,
    override val installTime: String,
    val drawable: Drawable
) : AppInfo(sourceDir, name, packageName, installTime)