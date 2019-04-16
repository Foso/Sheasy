package de.jensklingenberg.sheasy.ui.apps

import de.jensklingenberg.sheasy.model.AppInfo
import de.jensklingenberg.sheasy.ui.common.BaseDataSourceItem
import de.jensklingenberg.sheasy.ui.common.OnEntryClickListener



class AppInfoSourceItem(appInfo: AppInfo, var onEntryClickListener: AppInfoViewHolder.OnClick? = null) :
    BaseDataSourceItem<AppInfo>(AppInfoViewHolder::class.java) {


    override fun areItemsTheSameInner(other: BaseDataSourceItem<AppInfo>): Boolean = false

    override fun areContentsTheSameInner(other: BaseDataSourceItem<AppInfo>): Boolean = false


    init {
        setPayload(appInfo)
    }


}

inline fun AppInfo.toAppInfoSourceItem(onEntryClickListener: AppInfoViewHolder.OnClick? = null): AppInfoSourceItem {
    return AppInfoSourceItem(this, onEntryClickListener)
}
