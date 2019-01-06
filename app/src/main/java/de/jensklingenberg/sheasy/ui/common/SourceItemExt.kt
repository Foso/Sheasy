package de.jensklingenberg.sheasy.ui.common

import de.jensklingenberg.sheasy.ui.apps.AppInfoSourceItem
import de.jensklingenberg.sheasy.ui.files.FileResponseSourceItem
import de.jensklingenberg.sheasy.model.AppInfo
import de.jensklingenberg.sheasy.model.FileResponse


fun GenericListItem.toSourceItem(onEntryClickListener: OnEntryClickListener? = null): GenericListItemSourceItem {
    return GenericListItemSourceItem(this, onEntryClickListener)
}

inline fun AppInfo.toSourceItem(onEntryClickListener: OnEntryClickListener? = null): AppInfoSourceItem {
    return AppInfoSourceItem(this, onEntryClickListener)
}

fun FileResponse.toSourceitem(onEntryClickListener: OnEntryClickListener? = null): FileResponseSourceItem {
    return FileResponseSourceItem(this, onEntryClickListener)
}