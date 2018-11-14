package ui.apps

import model.AppFile
import ui.ReactPresenter

interface AppsContract {

    interface View {

        fun setData(apps: List<AppFile>)
    }

    interface Presenter : ReactPresenter {

        fun getApps()

        fun onSearch(query: String)
    }

}