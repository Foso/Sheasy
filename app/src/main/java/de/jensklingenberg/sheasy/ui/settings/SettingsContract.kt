package de.jensklingenberg.sheasy.ui.settings

import android.content.Intent
import de.jensklingenberg.sheasy.ui.common.BaseDataSourceItem
import de.jensklingenberg.sheasy.ui.common.MvpPresenter

interface SettingsContract {
    interface View {
        fun setData(items: List<BaseDataSourceItem<*>>)

    }

    interface Presenter : MvpPresenter {
        fun stopService(intent: Intent)
        fun startService(intent: Intent)
    }


    interface OnEntryClickListener {
        fun onItemClicked(payload: Any)

    }

}