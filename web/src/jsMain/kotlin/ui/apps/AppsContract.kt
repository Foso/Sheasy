package ui.apps

import model.AppResponse
import model.Error
import ui.common.ReactPresenter

interface AppsContract {

    interface View {

        fun setData(apps: List<AppResponse>)
        fun showError(error:Error)

    }

    interface Presenter : ReactPresenter {

        fun getApps()

        fun onSearch(query: String)
    }

}