package de.jensklingenberg.react.ui.about

import components.listview.SourceItem
import de.jensklingenberg.model.App
import de.jensklingenberg.model.Error
import de.jensklingenberg.react.ui.common.ReactPresenter

interface AboutContract{

    interface View{
        fun setData(items: List<SourceItem>)
        fun showError(error: Error)
    }

    interface Presenter:ReactPresenter{

    }
}

