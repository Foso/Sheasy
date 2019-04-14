package de.jensklingenberg.sheasy.ui.apps

import de.jensklingenberg.sheasy.model.AppInfo
import de.jensklingenberg.sheasy.ui.common.BaseDataSourceItem
import de.jensklingenberg.sheasy.ui.common.MvpPresenter
import de.jensklingenberg.sheasy.ui.common.OnEntryClickListener

interface AppsContract {
    interface View {
        fun setData(list: List<BaseDataSourceItem<*>>)
        fun showError(it: Throwable?)


        fun onMoreButtonClicked(view: android.view.View, payload: Any)
    }

    interface Presenter : MvpPresenter, AppInfoViewHolder.OnClick {
        fun searchApp(toString: String)
        fun extractApp(appInfo: AppInfo): Boolean
        fun shareApp(appInfo: AppInfo)
    }
}