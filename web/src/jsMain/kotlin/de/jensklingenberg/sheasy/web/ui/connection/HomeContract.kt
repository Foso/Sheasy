package de.jensklingenberg.sheasy.web.ui.connection

import de.jensklingenberg.sheasy.web.model.SourceItem
import de.jensklingenberg.sheasy.web.ui.common.ReactPresenter

interface ConnectionContract {

    interface View {
        fun setData(items: List<SourceItem>)
    }

    interface Presenter : ReactPresenter

}