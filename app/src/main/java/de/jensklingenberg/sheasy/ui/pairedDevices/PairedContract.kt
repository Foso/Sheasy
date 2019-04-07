package de.jensklingenberg.sheasy.ui.pairedDevices

import android.view.View
import de.jensklingenberg.sheasy.ui.common.BaseDataSourceItem
import de.jensklingenberg.sheasy.ui.common.MvpPresenter
import de.jensklingenberg.sheasy.web.model.Device

interface PairedContract {

    interface View {
            fun setData(list: List<BaseDataSourceItem<*>>)
        fun showContextMenu(device: Device,view: android.view.View)

    }

    interface Presenter : MvpPresenter , DeviceListItemSourceItem.OnEntryClickListener{
        fun revokeDevice(appInfo: Device)


    }

}