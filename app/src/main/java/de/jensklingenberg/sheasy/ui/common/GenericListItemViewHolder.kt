package de.jensklingenberg.sheasy.ui.common

import android.os.Bundle
import android.view.ViewGroup
import de.jensklingenberg.sheasy.R
import de.jensklingenberg.sheasy.model.GenericListItemSourceItem
import kotlinx.android.synthetic.main.list_item_generic.view.*

class GenericListItemViewHolder(viewParent: ViewGroup) :
    BaseViewHolder<GenericListItemSourceItem>(viewParent, R.layout.list_item_generic) {


    override fun onBindViewHolder(item: Any, diff: Bundle) {

        val listItem = (item as GenericListItemSourceItem).getPayload()

        listItem?.let {
            itemView.apply {
                title.text = listItem.title
                caption.text = listItem.caption
                icon.setImageResource(listItem.drawable)
                setOnClickListener {
                    item.onEntryClickListener?.onItemClicked(item)
                }
            }
        }
    }
}