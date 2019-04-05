package de.jensklingenberg.sheasy.web.ui.share

import de.jensklingenberg.sheasy.web.model.SourceItem
import de.jensklingenberg.sheasy.web.ui.common.ReactPresenter
import de.jensklingenberg.sheasy.web.usecase.NotificationOptions

interface ShareContract {

    interface View {

        fun setData(items: List<SourceItem>)
        fun showMessage(notificationOptions: SourceItem)


    }

    interface Presenter : ReactPresenter {


        fun send(message: String)
    }

}