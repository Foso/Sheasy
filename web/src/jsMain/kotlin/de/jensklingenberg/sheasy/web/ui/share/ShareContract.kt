package de.jensklingenberg.sheasy.web.ui.share

import de.jensklingenberg.sheasy.web.model.SourceItem
import de.jensklingenberg.sheasy.web.ui.common.ReactPresenter

interface ShareContract {

    interface View {
        fun setData(items: List<SourceItem>)
        fun showMessage(notificationOptions: SourceItem)
        fun setConnectedMessage(message: String)
    }

    interface Presenter : ReactPresenter {
        fun send(message: String)
    }

}