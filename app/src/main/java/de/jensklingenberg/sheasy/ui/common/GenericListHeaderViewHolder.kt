package de.jensklingenberg.sheasy.ui.common

import android.os.Bundle
import android.view.ViewGroup
import de.jensklingenberg.sheasy.R
import kotlinx.android.synthetic.main.list_item_header_generic.view.*


class GenericListHeaderViewHolder(viewParent: ViewGroup) :
    BaseViewHolder<GenericListHeaderSourceItem>(viewParent, R.layout.list_item_header_generic) {


    override fun onBindViewHolder(sourceItem: Any, diff: Bundle) {

        val itemTitle = (sourceItem as GenericListHeaderSourceItem).getPayload()

        itemView.apply {
            headerTitle.text = itemTitle

        }


    }
}