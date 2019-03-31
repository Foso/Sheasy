package de.jensklingenberg.sheasy.ui.pairedDevices

import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import de.jensklingenberg.sheasy.R
import de.jensklingenberg.sheasy.ui.common.BaseViewHolder
import kotlinx.android.synthetic.main.list_item_generic.view.*

class DeviceListItemViewHolder(viewParent: ViewGroup) :
    BaseViewHolder<DeviceListItemSourceItem>(viewParent, R.layout.list_item_generic) {


    override fun onBindViewHolder(item: Any, diff: Bundle) {

        val listItem = (item as DeviceListItemSourceItem).getPayload()

        listItem?.let {
            itemView.apply {
                title.text = listItem.name
                caption.text = listItem.ip
                icon.setImageResource( R.drawable.ic_smartphone_black_24dp)


                    moreBtn.visibility = View.VISIBLE
                    moreBtn.setOnClickListener {
                        item.onEntryClickListener?.onMoreButtonClicked(it, listItem)
                    }


            }
        }
    }
}