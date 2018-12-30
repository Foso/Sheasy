package de.jensklingenberg.react.ui.apps

import de.jensklingenberg.model.App
import de.jensklingenberg.model.Error
import de.jensklingenberg.react.ui.common.ReactPresenter

interface AppsContract {

    interface View {
        fun setData(apps: List<App>)
        fun showError(error: Error)
    }

    interface Presenter : ReactPresenter {
        fun getApps()
        fun onSearch(query: String)
    }

}