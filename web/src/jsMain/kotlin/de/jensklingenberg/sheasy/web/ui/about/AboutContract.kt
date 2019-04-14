package de.jensklingenberg.sheasy.web.ui.about

import de.jensklingenberg.sheasy.web.model.SourceItem
import de.jensklingenberg.sheasy.model.SheasyError
import de.jensklingenberg.sheasy.web.model.OnEntryClickListener
import de.jensklingenberg.sheasy.web.ui.common.ReactPresenter

interface AboutContract{

    interface View{
        fun setData(items: List<SourceItem>)
        fun showError(error: SheasyError)
    }

    interface Presenter: ReactPresenter, OnEntryClickListener
}

