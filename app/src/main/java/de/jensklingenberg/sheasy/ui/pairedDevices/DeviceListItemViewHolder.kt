package de.jensklingenberg.sheasy.ui.pairedDevices

import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import de.jensklingenberg.sheasy.R
import de.jensklingenberg.sheasy.ui.common.BaseViewHolder
import kotlinx.android.synthetic.main.list_item_generic.view.*

class DeviceListItemViewHolder(viewParent: ViewGroup) :
    BaseViewHolder<DeviceListItemSourceItem>(viewParent, R.layout.list_item_generic) {


    override fun onBindViewHolder(sourceItem: Any, diff: Bundle) {

        val device = (sourceItem as DeviceListItemSourceItem).getPayload()

        device?.let {
            itemView.apply {
                title.text = if (device.name.isEmpty()) {
                    device.ip
                } else {
                    device.name
                }
                caption.text = device.ip
                icon.setImageResource(R.drawable.ic_smartphone_black_24dp)

                moreBtn.visibility = View.VISIBLE
                moreBtn.setOnClickListener {
                    sourceItem.onMoreButtonClickFunction(it, sourceItem, device)
                }


            }
        }
    }

}