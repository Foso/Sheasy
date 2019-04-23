package de.jensklingenberg.sheasy.web.ui.screenshare

import de.jensklingenberg.sheasy.web.network.MyWebSocket
import de.jensklingenberg.sheasy.web.ui.common.ReactPresenter

interface ScreenshareContract {

    interface View {

        fun setData(base64: String)
    }

    interface Presenter : ReactPresenter,
        MyWebSocket.WebSocketListener {


    }

}