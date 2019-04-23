package de.jensklingenberg.sheasy.ui.eventlog

import de.jensklingenberg.sheasy.model.Event
import de.jensklingenberg.sheasy.ui.common.BaseDataSourceItem
import de.jensklingenberg.sheasy.ui.common.OnEntryClickListener


class EventSourceItem(
    genericListItem: Event,
    var onEntryClickListener: OnEntryClickListener? = null
) :
    BaseDataSourceItem<Event>(EventItemViewHolder::class.java) {


    override fun areItemsTheSameInner(other: BaseDataSourceItem<Event>): Boolean {
        return false
    }

    override fun areContentsTheSameInner(other: BaseDataSourceItem<Event>): Boolean {
        return false
    }


    init {
        setPayload(genericListItem)
    }


}