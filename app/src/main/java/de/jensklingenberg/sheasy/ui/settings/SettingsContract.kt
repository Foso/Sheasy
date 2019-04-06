package de.jensklingenberg.sheasy.ui.settings

import de.jensklingenberg.sheasy.ui.common.BaseDataSourceItem

interface SettingsContract {
    interface View {
        fun setData(items: List<BaseDataSourceItem<*>>)
        fun onItemClicked(payload: Any)
    }

    interface Presenter {
        fun onCreate()
    }

}