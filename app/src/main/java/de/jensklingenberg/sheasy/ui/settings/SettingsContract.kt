package de.jensklingenberg.sheasy.ui.settings

import de.jensklingenberg.sheasy.ui.common.BaseDataSourceItem

interface SettingsContract {
    interface View {
        fun setData(items: List<BaseDataSourceItem<*>>)
    }

    interface Presenter : OnEntryClickListener {
        fun onCreate()
    }


    interface OnEntryClickListener {
        fun onItemClicked(payload: Any)

    }

}