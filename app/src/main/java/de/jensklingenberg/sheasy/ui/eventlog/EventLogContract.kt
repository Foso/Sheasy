package de.jensklingenberg.sheasy.ui.eventlog

import de.jensklingenberg.sheasy.ui.common.BaseDataSourceItem

interface EventLogContract {


    interface View {
        fun setData(items: List<BaseDataSourceItem<*>>)
        fun onItemClicked(payload: Any)
    }

    interface Presenter {
        fun onCreate()
    }

}