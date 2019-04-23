package de.jensklingenberg.sheasy.ui.files

import android.os.Bundle
import android.view.View
import android.view.View.VISIBLE
import android.view.ViewGroup
import androidx.appcompat.widget.PopupMenu
import com.jakewharton.rxbinding3.appcompat.itemClicks
import de.jensklingenberg.sheasy.R
import de.jensklingenberg.sheasy.model.FileResponse
import de.jensklingenberg.sheasy.ui.common.BaseViewHolder
import kotlinx.android.synthetic.main.list_item_generic.view.*

class SharedFolderViewHolder(viewParent: ViewGroup) :
    BaseViewHolder<SharedFolderSourceItem>(viewParent, R.layout.list_item_generic) {

    interface OnEntryClickListener {
        fun onItemClicked(payload: Any)
        fun onPopupMenuClicked(fileResponse: FileResponse, id: Int)

    }

    override fun onBindViewHolder(item2: Any, diff: Bundle) {

        val fileResponse = (item2 as SharedFolderSourceItem).getPayload()

        fileResponse?.let {
            itemView.apply {
                title.text = fileResponse.name
                caption.text = fileResponse.path

                icon.setImageResource(R.drawable.ic_router_green_700_24dp)

                item.setOnClickListener {
                 //   item2.onEntryClickListener?.onItemClicked(fileResponse)
                }
                moreBtn.visibility = VISIBLE
                moreBtn.setOnClickListener {

                    val popup = setupContextMenu(it, item2, fileResponse)
                    popup.show()
                }
            }
        }


    }

    private fun setupContextMenu(
        view: View,
        sharedFolderSourceItem: SharedFolderSourceItem,
        fileResponse: FileResponse
    ): PopupMenu {
        return PopupMenu(view.context, view)
            .apply {
                menuInflater
                    .inflate(R.menu.shared_folder_actions, menu)
            }
            .also {
                it.itemClicks()
                    .doOnNext { menuItem ->
                        sharedFolderSourceItem.onEntryClickListener?.onPopupMenuClicked(
                            fileResponse,
                            menuItem.itemId
                        )
                    }.subscribe()
            }
    }


}