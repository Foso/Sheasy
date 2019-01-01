package de.jensklingenberg.sheasy.ui.apps

import de.jensklingenberg.sheasy.ui.common.BaseDataSourceItem
import de.jensklingenberg.sheasy.ui.common.OnEntryClickListener
import de.jensklingenberg.sheasy.web.model.AppInfo


class AppInfoSourceItem(appInfo: AppInfo, var onEntryClickListener: OnEntryClickListener? = null) :
    BaseDataSourceItem<AppInfo>(AppInfoViewHolder::class.java) {


    override fun areItemsTheSameInner(other: BaseDataSourceItem<AppInfo>): Boolean = false

    override fun areContentsTheSameInner(other: BaseDataSourceItem<AppInfo>): Boolean = false


    init {
        setPayload(appInfo)
    }


}