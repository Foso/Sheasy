package de.jensklingenberg.sheasy.web.ui.home

import de.jensklingenberg.sheasy.web.model.DrawerItems
import de.jensklingenberg.sheasy.web.model.SourceItem
import de.jensklingenberg.sheasy.web.ui.common.ReactPresenter

interface HomeContract{

    interface View{
        fun setData(items: List<SourceItem>)
    }
    interface Presenter: ReactPresenter

}