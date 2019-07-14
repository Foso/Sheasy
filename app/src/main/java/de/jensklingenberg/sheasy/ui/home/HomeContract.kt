package de.jensklingenberg.sheasy.ui.home

import android.content.Intent
import de.jensklingenberg.sheasy.ui.common.BaseDataSourceItem
import de.jensklingenberg.sheasy.ui.common.MvpPresenter
import de.jensklingenberg.sheasy.ui.common.OnEntryClickListener

interface HomeContract {
    interface View {
        fun setData(list: List<BaseDataSourceItem<*>>)
        fun navigateTo(navId: Int)
        fun setServerState(isRunning: Boolean)
    }

    interface Presenter : MvpPresenter, OnEntryClickListener {
        fun stopService(intent: Intent)
        fun startService(intent: Intent)

    }
}