package de.jensklingenberg.sheasy.ui.apps

import de.jensklingenberg.sheasy.model.AppInfo
import de.jensklingenberg.sheasy.ui.common.BaseDataSourceItem
import de.jensklingenberg.sheasy.ui.common.OnEntryClickListener


class AppInfoSourceItem(appInfo: AppInfo, var onEntryClickListener: OnEntryClickListener? = null) :
    BaseDataSourceItem<AppInfo>(AppInfoViewHolder::class.java) {


    override fun areItemsTheSameInner(other: BaseDataSourceItem<AppInfo>): Boolean {
        return false
    }

    override fun areContentsTheSameInner(other: BaseDataSourceItem<AppInfo>): Boolean {
        return false
    }


    init {
        setPayload(appInfo)
    }


}