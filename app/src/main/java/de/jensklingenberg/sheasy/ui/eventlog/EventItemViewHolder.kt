package de.jensklingenberg.sheasy.ui.eventlog

import android.os.Bundle
import android.view.ViewGroup
import de.jensklingenberg.sheasy.R
import de.jensklingenberg.sheasy.ui.common.BaseViewHolder
import kotlinx.android.synthetic.main.list_item_generic.view.*

class EventItemViewHolder(viewParent: ViewGroup) :
    BaseViewHolder<EventSourceItem>(viewParent, R.layout.list_item_generic) {


    override fun onBindViewHolder(item: Any, diff: Bundle) {

        val listItem = (item as EventSourceItem).getPayload()

        listItem?.let {
            itemView.apply {
                title.text = listItem.text
                caption.text = listItem.category.title
                // icon.setImageResource(listItem.drawable)
                setOnClickListener {
                    item.onEntryClickListener?.onItemClicked(item)
                }


            }
        }
    }
}