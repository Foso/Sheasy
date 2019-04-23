package de.jensklingenberg.sheasy.ui.common

data class GenericToggleItem(
    val title: String,
    val caption: String,
    val drawable: Int,
    val checkedValue: Boolean = false,
    val onToggle: (Boolean) -> Unit
)
