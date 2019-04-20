package de.jensklingenberg.sheasy.ui.pairedDevices

import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.PopupMenu
import de.jensklingenberg.sheasy.R
import de.jensklingenberg.sheasy.model.Device
import de.jensklingenberg.sheasy.ui.common.BaseViewHolder
import kotlinx.android.synthetic.main.list_item_generic.view.*

class DeviceListItemViewHolder(viewParent: ViewGroup) :
    BaseViewHolder<DeviceListItemSourceItem>(viewParent, R.layout.list_item_generic) {

    interface OnEntryClickListener {
        fun onContextMenuClick(device: Device, id: Int)

    }

    override fun onBindViewHolder(item: Any, diff: Bundle) {

        val device = (item as DeviceListItemSourceItem).getPayload()

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
                    val popup = setupContextMenu(it, item, device)
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
                menuInflater
                    .inflate(R.menu.paired_devices_actions, menu)

                setOnMenuItemClickListener { menuItem ->
                    when (menuItem.itemId) {
                        R.id.menu_revoke -> {
                            item?.onEntryClickListener?.onContextMenuClick(device, menuItem.itemId)
                            // pairedPresenter.revokeDevice(device)
                            true
                        }
                        else -> {
                            true
                        }
                    }
                }

            }
    }
}