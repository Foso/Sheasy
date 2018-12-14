package ui.screenshare

import ui.common.ReactPresenter

interface ScreenshareContract {

    interface View {

        fun setData(apps: String)
    }

    interface Presenter : ReactPresenter {


        fun onSearch(query: String)
    }

}