package de.jensklingenberg.sheasy.ui.apps

import android.os.Bundle
import android.view.ViewGroup
import de.jensklingenberg.sheasy.R
import de.jensklingenberg.sheasy.ui.common.BaseViewHolder
import kotlinx.android.synthetic.main.list_item_generic.view.*

class AppInfoViewHolder(viewParent: ViewGroup) :
    BaseViewHolder<AppInfoSourceItem>(viewParent, R.layout.list_item_generic) {


    override fun onBindViewHolder(item: Any, diff: Bundle) {

        val appInfoSourceItem = (item as AppInfoSourceItem).getPayload()

        appInfoSourceItem?.let {
            itemView.apply {
                title.text = it.name
                caption.text = it.packageName
                icon.setImageDrawable(it.icon)
                setOnClickListener {
                    item.onEntryClickListener?.onItemClicked(item)
                }
            }
        }
    }
}