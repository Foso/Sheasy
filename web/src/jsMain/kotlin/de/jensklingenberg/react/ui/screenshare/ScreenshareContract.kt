package de.jensklingenberg.react.ui.screenshare

import de.jensklingenberg.react.ui.common.ReactPresenter

interface ScreenshareContract {

    interface View {

        fun setData(base64: String)
    }

    interface Presenter : ReactPresenter {


    }

}