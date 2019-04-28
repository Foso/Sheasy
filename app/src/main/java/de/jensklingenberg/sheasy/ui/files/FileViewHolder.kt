package de.jensklingenberg.sheasy.ui.files

import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.PopupMenu
import com.jakewharton.rxbinding3.appcompat.itemClicks
import de.jensklingenberg.sheasy.R
import de.jensklingenberg.sheasy.model.FileResponse
import de.jensklingenberg.sheasy.ui.common.BaseViewHolder
import kotlinx.android.synthetic.main.list_item_generic.view.*

class FileViewHolder(viewParent: ViewGroup) :
    BaseViewHolder<FileSourceItem>(viewParent, R.layout.list_item_shared) {


    interface OnEntryClickListener {
        fun onPopupMenuClicked(fileResponse: FileResponse, id: Int)
    }

    override fun onBindViewHolder(item2: Any, diff: Bundle) {

        val fileResponse = (item2 as FileSourceItem).getPayload()

        fileResponse?.let {
            itemView.apply {
                title.text = fileResponse.name
                caption.text = fileResponse.path
                moreBtn.visibility = View.VISIBLE

                icon.setImageResource(R.drawable.ic_insert_drive_file_grey_700_24dp)
                moreBtn.setOnClickListener {
                    val popup = setupContextMenu(it, item2, FileResponse(fileResponse.name,fileResponse.path))
                    popup.show()

                }
            }
        }


    }

    private fun setupContextMenu(
        it: View,
        item2: FileSourceItem,
        fileResponse: FileResponse
    ): PopupMenu {
        return PopupMenu(it.context, it)
            .apply {
                menuInflater
                    .inflate(R.menu.files_actions, menu)
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