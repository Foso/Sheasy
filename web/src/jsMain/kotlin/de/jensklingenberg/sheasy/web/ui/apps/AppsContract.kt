package de.jensklingenberg.sheasy.web.ui.apps

import de.jensklingenberg.sheasy.web.model.response.App
import de.jensklingenberg.sheasy.model.Error
import de.jensklingenberg.sheasy.web.ui.common.ReactPresenter
import org.w3c.dom.events.Event

interface AppsContract {

    interface View {

        fun setData(apps: List<AppSourceItem>)

        fun showError(error: Error)
        fun handleClickListItem(event: Event, app: App)
    }

    interface Presenter : ReactPresenter {
        fun getApps()
        fun onSearch(query: String)
        fun downloadApk(app: App?)
    }

}