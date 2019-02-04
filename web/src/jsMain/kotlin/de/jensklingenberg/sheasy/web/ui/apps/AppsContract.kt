package de.jensklingenberg.sheasy.web.ui.apps

import de.jensklingenberg.sheasy.web.model.response.App
import de.jensklingenberg.sheasy.web.model.Error
import de.jensklingenberg.sheasy.web.ui.common.ReactPresenter

interface AppsContract {

    interface View {
        fun setData(apps: List<App>)
        fun showError(error: Error)
    }

    interface Presenter : ReactPresenter {
        fun getApps()
        fun onSearch(query: String)
        fun downloadApk(app: App?)
    }

}