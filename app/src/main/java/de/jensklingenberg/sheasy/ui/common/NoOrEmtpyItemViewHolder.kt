package de.jensklingenberg.sheasy.ui.common

import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import de.jensklingenberg.sheasy.R
import kotlinx.android.synthetic.main.settings_toggle_entry.view.*

class NoOrEmtpyItemViewHolder(viewParent: ViewGroup) :
    BaseViewHolder<NoOrEmptyContentSourceItem>(viewParent, R.layout.item_empty) {


    override fun onBindViewHolder(item: Any, diff: Bundle) {

        val listItem = (item as NoOrEmptyContentSourceItem).getPayload()

        listItem?.let {
            itemView.apply {
                itemTv.text = listItem.message

            }
        }
    }
}