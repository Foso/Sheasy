package de.jensklingenberg.sheasy.ui.apps

import de.jensklingenberg.sheasy.model.AppInfo
import de.jensklingenberg.sheasy.ui.common.MvpPresenter

interface AppsContract{
    interface View{
        fun setData(list: List<AppInfoSourceItem>)
        fun showError(it: Throwable?)
        fun shareApp(appInfo: AppInfo)
        fun extractApp(appInfo: AppInfo)

    }
    interface Presenter:MvpPresenter {
        fun searchApp(toString: String)
        fun extractApp(appInfo: AppInfo):Boolean
    }
}