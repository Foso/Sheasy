package de.jensklingenberg.sheasy.web.ui.share

import de.jensklingenberg.sheasy.web.model.SourceItem
import de.jensklingenberg.sheasy.web.network.MyWebSocket
import de.jensklingenberg.sheasy.web.ui.common.ReactPresenter
import de.jensklingenberg.sheasy.web.usecase.NotificationOptions

interface ShareContract {

    interface View {

        fun setData(items: List<SourceItem>)
        fun showMessage(notificationOptions: SourceItem)
        fun setConnectedMessage(message: String)

    }

    interface Presenter : ReactPresenter, MyWebSocket.WebSocketListener {


        fun send(message: String)
    }

}