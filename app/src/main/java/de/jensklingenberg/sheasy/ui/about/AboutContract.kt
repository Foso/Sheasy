package de.jensklingenberg.sheasy.ui.about

import de.jensklingenberg.sheasy.ui.common.BaseDataSourceItem
import de.jensklingenberg.sheasy.ui.common.OnEntryClickListener

interface AboutContract {
    
    interface View {
        fun setData(items: List<BaseDataSourceItem<*>>)
        fun onItemClicked(payload: Any)
    }

    interface Presenter : OnEntryClickListener {
        fun onCreate()
    }

}