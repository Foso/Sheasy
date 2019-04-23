package de.jensklingenberg.sheasy.ui.pairedDevices

import de.jensklingenberg.sheasy.model.Device
import de.jensklingenberg.sheasy.ui.common.BaseDataSourceItem


class DeviceListItemSourceItem(
    genericListItem: Device,
    var onEntryClickListener: DeviceListItemViewHolder.OnEntryClickListener? = null
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


}