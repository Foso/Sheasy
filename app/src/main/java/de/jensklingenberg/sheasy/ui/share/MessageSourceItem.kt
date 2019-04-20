package de.jensklingenberg.sheasy.ui.share

import android.view.View
import de.jensklingenberg.sheasy.model.Device
import de.jensklingenberg.sheasy.model.MessageEvent
import de.jensklingenberg.sheasy.ui.common.BaseDataSourceItem
import de.jensklingenberg.sheasy.ui.pairedDevices.DeviceListItemViewHolder


class MessageSourceItem(
    genericListItem: MessageEvent,
    var onEntryClickListener: OnEntryClickListener? = null
) :
    BaseDataSourceItem<MessageEvent>(MessageItemViewHolder::class.java) {


    override fun areItemsTheSameInner(other: BaseDataSourceItem<MessageEvent>): Boolean {
        return false
    }

    override fun areContentsTheSameInner(other: BaseDataSourceItem<MessageEvent>): Boolean {
        return false
    }


    init {
        setPayload(genericListItem)
    }


    interface OnEntryClickListener {
        fun onItemClicked(payload: Any)
        fun onMoreButtonClicked(view: View, payload: Any)

    }

}