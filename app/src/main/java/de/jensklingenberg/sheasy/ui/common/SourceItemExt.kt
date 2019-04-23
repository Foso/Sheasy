package de.jensklingenberg.sheasy.ui.common

import de.jensklingenberg.sheasy.model.Event
import de.jensklingenberg.sheasy.ui.eventlog.EventSourceItem


fun GenericListItem.toSourceItem(onEntryClickListener: OnEntryClickListener? = null): GenericListItemSourceItem {
    return GenericListItemSourceItem(this, onEntryClickListener)
}

fun Event.toSourceItem(onEntryClickListener: OnEntryClickListener? = null): EventSourceItem {
    return EventSourceItem(this, onEntryClickListener)
}


fun GenericToggleItem.toSourceItem(onEntryClickListener: OnEntryClickListener? = null): GenericToggleItemSourceItem {
    return GenericToggleItemSourceItem(this, onEntryClickListener)
}


