package de.jensklingenberg.sheasy.ui.apps

import de.jensklingenberg.sheasy.model.AndroidAppInfo
import de.jensklingenberg.sheasy.ui.common.BaseDataSourceItem


class AppInfoSourceItem(appInfo: AndroidAppInfo, var onEntryClickListener: AppInfoViewHolder.OnClick? = null) :
    BaseDataSourceItem<AndroidAppInfo>(AppInfoViewHolder::class.java) {


    override fun areItemsTheSameInner(other: BaseDataSourceItem<AndroidAppInfo>): Boolean {
        return getPayload() == other.getPayload()
    }

    override fun areContentsTheSameInner(other: BaseDataSourceItem<AndroidAppInfo>): Boolean {
        return getPayload()?.packageName == other.getPayload()?.packageName
    }


    init {
        setPayload(appInfo)
    }


}

