package de.jensklingenberg.sheasy.ui.share

import de.jensklingenberg.sheasy.model.MessageEvent
import de.jensklingenberg.sheasy.ui.common.BaseDataSourceItem


class OutgoingMessageSourceItem(
    messageEvent: MessageEvent,
    var onEntryClickListener: OnEntryClickListener? = null
) :
    BaseDataSourceItem<MessageEvent>(OutgoingMessageItemViewHolder::class.java) {


    override fun areItemsTheSameInner(other: BaseDataSourceItem<MessageEvent>): Boolean {
        return false
    }

    override fun areContentsTheSameInner(other: BaseDataSourceItem<MessageEvent>): Boolean {
        return false
    }


    init {
        setPayload(messageEvent)
    }


    interface OnEntryClickListener {
        fun onItemClicked(payload: Any)

    }

}