package de.jensklingenberg.sheasy.ui.apps

import de.jensklingenberg.sheasy.ui.common.BaseDataSourceItem
import de.jensklingenberg.sheasy.ui.common.MvpPresenter

interface AppsContract {
    interface View {
        fun setData(list: List<BaseDataSourceItem<*>>)
        fun showError(it: Throwable)
        fun showMessage(resId: Int)
    }

    interface Presenter : MvpPresenter, AppInfoViewHolder.OnClick {
        fun searchApp(packageName: String)
    }
}