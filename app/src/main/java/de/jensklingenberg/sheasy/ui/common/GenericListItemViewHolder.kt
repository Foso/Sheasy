package de.jensklingenberg.sheasy.ui.common

import android.os.Bundle
import android.view.ViewGroup
import de.jensklingenberg.sheasy.R
import kotlinx.android.synthetic.main.list_item_generic.view.*

class GenericListItemViewHolder(viewParent: ViewGroup) :
    BaseViewHolder<GenericListItemSourceItem>(viewParent, R.layout.list_item_generic) {


    override fun onBindViewHolder(sourceItem: Any, diff: Bundle) {

        val listItem = (sourceItem as GenericListItemSourceItem).getPayload()

        listItem?.let {
            itemView.apply {
                title.text = listItem.title
                caption.text = listItem.caption
                if (listItem.drawable != NO_IMAGE) {
                    icon.setImageResource(listItem.drawable)
                }

                setOnClickListener {
                    sourceItem.onEntryClickListener?.onItemClicked(sourceItem)
                }


            }
        }
    }
}