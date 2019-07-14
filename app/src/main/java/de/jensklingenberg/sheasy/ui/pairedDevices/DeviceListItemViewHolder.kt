package de.jensklingenberg.sheasy.ui.pairedDevices

import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.PopupMenu
import com.jakewharton.rxbinding3.appcompat.itemClicks
import de.jensklingenberg.sheasy.R
import de.jensklingenberg.sheasy.model.AuthorizationType
import de.jensklingenberg.sheasy.model.Device
import de.jensklingenberg.sheasy.ui.common.BaseViewHolder
import kotlinx.android.synthetic.main.list_item_generic.view.*

class DeviceListItemViewHolder(viewParent: ViewGroup) :
    BaseViewHolder<DeviceListItemSourceItem>(viewParent, R.layout.list_item_generic) {

    interface OnEntryClickListener {
        fun onContextMenuClick(device: Device, id: Int)

    }

    override fun onBindViewHolder(sourceItem: Any, diff: Bundle) {

        val device = (sourceItem as DeviceListItemSourceItem).getPayload()

        device?.let {
            itemView.apply {
                title.text = if (device.name.isEmpty()) {
                    device.ip
                } else {
                    device.name
                }
                caption.text = device.ip
                icon.setImageResource(R.drawable.ic_smartphone_black_24dp)

                moreBtn.visibility = View.VISIBLE
                moreBtn.setOnClickListener {
                    val popup = setupContextMenu(it, sourceItem, device)
                    popup.show()

                }


            }
        }
    }

    private fun setupContextMenu(
        it: View,
        item: DeviceListItemSourceItem,
        device: Device
    ): PopupMenu {
        return PopupMenu(it.context, it)
            .apply {
                if (device.authorizationType == AuthorizationType.AUTHORIZED) {
                    menuInflater
                        .inflate(R.menu.paired_devices_actions, menu)
                } else if (device.authorizationType == AuthorizationType.REVOKED) {
                    menuInflater
                        .inflate(R.menu.revoked_devices_actions, menu)
                }
            }.also {
                it.itemClicks()
                    .doOnNext { menuItem ->

                        item.onEntryClickListener?.onContextMenuClick(
                            device,
                            menuItem.itemId
                        )

                    }.subscribe()
            }
    }
}