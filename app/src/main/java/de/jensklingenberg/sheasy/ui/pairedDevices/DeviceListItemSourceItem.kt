package de.jensklingenberg.sheasy.ui.pairedDevices

import android.view.View
import de.jensklingenberg.sheasy.model.Device
import de.jensklingenberg.sheasy.ui.common.BaseDataSourceItem


class DeviceListItemSourceItem(
    genericListItem: Device,
    var onMoreButtonClickFunction: (View, DeviceListItemSourceItem, Device) -> Unit = { _, _, _ -> }
) :
    BaseDataSourceItem<Device>(DeviceListItemViewHolder::class.java) {


    override fun areItemsTheSameInner(other: BaseDataSourceItem<Device>): Boolean {
        return getPayload() == other.getPayload()
    }

    override fun areContentsTheSameInner(other: BaseDataSourceItem<Device>): Boolean {
        return getPayload()?.ip == other.getPayload()?.ip
    }


    init {
        setPayload(genericListItem)
    }


}