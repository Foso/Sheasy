package de.jensklingenberg.sheasy.ui.share

import de.jensklingenberg.sheasy.ui.common.BaseDataSourceItem
import de.jensklingenberg.sheasy.ui.common.MvpPresenter

interface ShareContract {
    interface View {
        fun setData(items: List<BaseDataSourceItem<*>>)

    }

    interface Presenter : MvpPresenter
}