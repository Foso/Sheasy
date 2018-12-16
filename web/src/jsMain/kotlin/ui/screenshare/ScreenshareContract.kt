package ui.screenshare

import ui.common.ReactPresenter

interface ScreenshareContract {

    interface View {

        fun setData(base64: String)
    }

    interface Presenter : ReactPresenter {


    }

}