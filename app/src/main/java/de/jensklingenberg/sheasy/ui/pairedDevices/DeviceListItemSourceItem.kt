package de.jensklingenberg.sheasy.ui.pairedDevices

import android.view.View
import de.jensklingenberg.sheasy.ui.common.BaseDataSourceItem
import de.jensklingenberg.sheasy.ui.common.OnEntryClickListener
import de.jensklingenberg.sheasy.web.model.Device


class DeviceListItemSourceItem(
    genericListItem: Device,
    var onEntryClickListener: OnEntryClickListener? = null
) :
    BaseDataSourceItem<Device>(DeviceListItemViewHolder::class.java) {


    override fun areItemsTheSameInner(other: BaseDataSourceItem<Device>): Boolean {
        return false
    }

    override fun areContentsTheSameInner(other: BaseDataSourceItem<Device>): Boolean {
        return false
    }


    init {
        setPayload(genericListItem)
    }

    interface OnEntryClickListener {
        fun onMoreButtonClicked(view: View, payload: Any)

    }


}