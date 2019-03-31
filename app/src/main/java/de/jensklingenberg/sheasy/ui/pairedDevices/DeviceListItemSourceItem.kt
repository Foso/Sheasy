package de.jensklingenberg.sheasy.ui.pairedDevices

import de.jensklingenberg.sheasy.ui.common.BaseDataSourceItem
import de.jensklingenberg.sheasy.ui.common.GenericListItem
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


}