package ui.apps

import model.AppResponse
import ui.common.ReactPresenter

interface AppsContract {

    interface View {

        fun setData(apps: List<AppResponse>)
    }

    interface Presenter : ReactPresenter {

        fun getApps()

        fun onSearch(query: String)
    }

}