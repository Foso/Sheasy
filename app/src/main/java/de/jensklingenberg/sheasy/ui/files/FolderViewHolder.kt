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
    BaseViewHolder<FileSourceItem>(viewParent, R.layout.list_item_generic) {


    interface OnEntryClickListener {
        fun onItemClicked(fileResponse: FileResponse)
        fun onPopupMenuClicked(fileResponse: FileResponse, id: Int)
        fun onPopupMenuClicked(view: View, fileResponse: FileResponse)

    }

    override fun onBindViewHolder(sourceItem: Any, diff: Bundle) {

        val file = (sourceItem as FolderSourceItem).getPayload()

        file?.let {
            itemView.apply {
                title.text = file.name
                caption.text = file.path
                moreBtn.visibility = VISIBLE

                if (sourceItem.isFolder) {
                    icon.setImageResource(R.drawable.ic_folder_grey_700_24dp)
                } else {
                    icon.setImageResource(R.drawable.ic_insert_drive_file_grey_700_24dp)
                }

                moreBtn.setOnClickListener {
                    val popup = setupContextMenu(it, sourceItem, FileResponse(file.name, file.path))
                    popup.show()

                }


                item.setOnClickListener {
                    sourceItem.onEntryClickListener?.onItemClicked(FileResponse(file.name, file.path))
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