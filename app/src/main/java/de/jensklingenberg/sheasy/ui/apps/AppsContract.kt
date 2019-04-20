package de.jensklingenberg.sheasy.ui.apps

import de.jensklingenberg.sheasy.model.AppInfo
import de.jensklingenberg.sheasy.ui.common.BaseDataSourceItem
import de.jensklingenberg.sheasy.ui.common.MvpPresenter
import io.reactivex.Completable
import io.reactivex.Single

interface AppsContract {
    interface View {
        fun setData(list: List<BaseDataSourceItem<*>>)
        fun showError(it: Throwable)
        fun showMessage(resId: Int)
    }

    interface Presenter : MvpPresenter, AppInfoViewHolder.OnClick {
        fun searchApp(toString: String)
        fun extractApp(appInfo: AppInfo): Completable
        fun shareApp(appInfo: AppInfo)
    }
}