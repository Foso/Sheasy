package de.jensklingenberg.sheasy.utils.extension

import de.jensklingenberg.sheasy.model.GenericListItem
import de.jensklingenberg.sheasy.model.GenericListItemSourceItem
import de.jensklingenberg.sheasy.ui.apps.AppInfoSourceItem
import de.jensklingenberg.sheasy.ui.common.OnEntryClickListener
import de.jensklingenberg.sheasy.ui.files.FileResponseSourceItem
import de.jensklingenberg.model.AppInfo
import de.jensklingenberg.model.FileResponse


fun GenericListItem.toSourceItem(onEntryClickListener: OnEntryClickListener? = null): GenericListItemSourceItem {
    return GenericListItemSourceItem(this, onEntryClickListener)
}

inline fun AppInfo.toSourceItem(onEntryClickListener: OnEntryClickListener? = null): AppInfoSourceItem {
    return AppInfoSourceItem(this, onEntryClickListener)
}

fun FileResponse.toSourceitem(onEntryClickListener: OnEntryClickListener? = null): FileResponseSourceItem {
    return FileResponseSourceItem(this, onEntryClickListener)
}