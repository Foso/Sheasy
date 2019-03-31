package de.jensklingenberg.sheasy.ui.about

import de.jensklingenberg.sheasy.ui.common.BaseDataSourceItem

interface AboutContract{


    interface View{
        fun setData(items :List<BaseDataSourceItem<*>>)
        fun onItemClicked(payload: Any)
    }
    interface Presenter{
        fun onCreate()
    }

}