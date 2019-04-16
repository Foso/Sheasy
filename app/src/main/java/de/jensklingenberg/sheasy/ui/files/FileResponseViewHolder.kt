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
import java.io.File

class FileResponseViewHolder(viewParent: ViewGroup) :
    BaseViewHolder<FileResponseSourceItem>(viewParent, R.layout.list_item_generic) {


    interface OnEntryClickListener {
        fun onItemClicked(payload: Any)
        fun onPopupMenuClicked(fileResponse: FileResponse,id:Int)

    }

    override fun onBindViewHolder(item2: Any, diff: Bundle) {

        val fileResponse = (item2 as FileResponseSourceItem).getPayload()

        fileResponse?.let {
            itemView.apply {
                title.text = fileResponse.name
                caption.text = fileResponse.path
                if (fileResponse.path.contains(".")) {
                    icon.setImageResource(R.drawable.ic_insert_drive_file_grey_700_24dp)

                } else {
                    icon.setImageResource(R.drawable.ic_folder_grey_700_24dp)

                }
                item.setOnClickListener {
                    item2.onEntryClickListener?.onItemClicked(fileResponse)
                }
                moreBtn.visibility = VISIBLE
                moreBtn.setOnClickListener {
                   // item2.onEntryClickListener?.onMoreButtonClicked(it, fileResponse)

                    val popup = PopupMenu(it.context, it)
                        .apply {
                            menuInflater
                                .inflate(R.menu.files_actions, menu)
                        }
                        .also {
                            it.itemClicks()
                                .doOnNext { menuItem ->
                                    when (menuItem.itemId) {
                                        R.id.menu_share -> {
                                            item2.onEntryClickListener?.onPopupMenuClicked(fileResponse,menuItem.itemId)
                                           // presenter.share(File(item.path))
                                        }
                                        R.id.menu_share_to_server -> {
                                            item2.onEntryClickListener?.onPopupMenuClicked(fileResponse,menuItem.itemId)


                                        }
                                    }
                                }.subscribe()
                        }
                    popup.show()

                }
            }
        }


    }


}