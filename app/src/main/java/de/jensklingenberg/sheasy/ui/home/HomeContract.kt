package de.jensklingenberg.sheasy.ui.home

import android.content.Intent
import de.jensklingenberg.sheasy.ui.common.BaseDataSourceItem
import de.jensklingenberg.sheasy.ui.common.MvpPresenter

interface HomeContract{
    interface View{
        fun setData(list: List<BaseDataSourceItem<*>>)
        fun navigateTo(navId: Int)
    }

    interface Presenter:MvpPresenter{
        fun stopService(intent: Intent)
        fun startService(intent: Intent)

    }
}