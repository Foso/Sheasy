package de.jensklingenberg.sheasy.ui.common

import de.jensklingenberg.sheasy.model.AppInfo
import de.jensklingenberg.sheasy.model.Event
import de.jensklingenberg.sheasy.model.FileResponse
import de.jensklingenberg.sheasy.ui.apps.AppInfoSourceItem
import de.jensklingenberg.sheasy.ui.apps.AppInfoViewHolder
import de.jensklingenberg.sheasy.ui.eventlog.EventSourceItem
import de.jensklingenberg.sheasy.ui.files.FileResponseSourceItem


fun GenericListItem.toSourceItem(onEntryClickListener: OnEntryClickListener? = null): GenericListItemSourceItem {
    return GenericListItemSourceItem(this, onEntryClickListener)
}

fun Event.toSourceItem(onEntryClickListener: OnEntryClickListener? = null): EventSourceItem {
    return EventSourceItem(this, onEntryClickListener)
}


fun GenericToggleItem.toSourceItem(onEntryClickListener: OnEntryClickListener? = null): GenericToggleItemSourceItem {
    return GenericToggleItemSourceItem(this, onEntryClickListener)
}


