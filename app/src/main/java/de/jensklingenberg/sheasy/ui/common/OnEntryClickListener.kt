package de.jensklingenberg.sheasy.ui.common

import android.view.View

interface OnEntryClickListener {
    fun onItemClicked(payload: Any)
    fun onMoreButtonClicked(view: View,payload: Any)

}