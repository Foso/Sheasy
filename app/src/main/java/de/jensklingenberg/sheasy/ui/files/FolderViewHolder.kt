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

class FolderViewHolder(viewParent: ViewGroup) :
    BaseViewHolder<FileResponseSourceItem>(viewParent, R.layout.list_item_generic) {


    interface OnEntryClickListener {
        fun onItemClicked(payload: Any)
        fun onPopupMenuClicked(fileResponse: FileResponse, id: Int)

    }

    override fun onBindViewHolder(item2: Any, diff: Bundle) {

        val fileResponse = (item2 as FolderSourceItem).getPayload()

        fileResponse?.let {
            itemView.apply {
                title.text = fileResponse.name
                caption.text = fileResponse.path
                moreBtn.visibility = VISIBLE
                icon.setImageResource(R.drawable.ic_folder_grey_700_24dp)
                moreBtn.setOnClickListener {
                    val popup = setupContextMenu(it, item2, fileResponse)
                    popup.show()

                }


                item.setOnClickListener {
                    item2.onEntryClickListener?.onItemClicked(fileResponse)
                }


            }
        }


    }

    private fun setupContextMenu(
        it: View,
        item2: FolderSourceItem,
        fileResponse: FileResponse
    ): PopupMenu {
        return PopupMenu(it.context, it)
            .apply {
                menuInflater
                    .inflate(R.menu.folders_actions, menu)
            }
            .also {
                it.itemClicks()
                    .doOnNext { menuItem ->

                        item2.onEntryClickListener?.onPopupMenuClicked(
                            fileResponse,
                            menuItem.itemId
                        )

                    }.subscribe()
            }
    }


}