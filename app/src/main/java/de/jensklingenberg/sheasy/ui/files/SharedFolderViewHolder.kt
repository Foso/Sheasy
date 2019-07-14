package de.jensklingenberg.sheasy.ui.files

import android.os.Bundle
import android.view.View
import android.view.View.VISIBLE
import android.view.ViewGroup
import de.jensklingenberg.sheasy.R
import de.jensklingenberg.sheasy.model.FileResponse
import de.jensklingenberg.sheasy.ui.common.BaseViewHolder
import kotlinx.android.synthetic.main.list_item_generic.view.*
import java.io.File

class SharedFolderViewHolder(viewParent: ViewGroup) :
    BaseViewHolder<SharedFolderSourceItem>(viewParent, R.layout.list_item_shared) {

    interface OnEntryClickListener {
        fun onItemClicked(payload: FileResponse)
        fun onPopupMenuClicked(fileResponse: FileResponse, id: Int)
        fun onPopupMenuClicked(view: View, fileResponse: FileResponse)
    }

    override fun onBindViewHolder(sourceItem: Any, diff: Bundle) {

        val file: File? = (sourceItem as SharedFolderSourceItem).getPayload()

        file?.let {
            itemView.apply {
                title.text = file.name
                caption.text = file.path


                if (sourceItem.isFolder) {
                    icon.setImageResource(R.drawable.ic_folder_grey_700_24dp)
                } else {
                    icon.setImageResource(R.drawable.ic_insert_drive_file_grey_700_24dp)
                }

                item.setOnClickListener {
                    sourceItem.onEntryClickListener?.onItemClicked(FileResponse(file.name, file.path))
                }
                moreBtn.visibility = VISIBLE
                moreBtn.setOnClickListener { view ->
                    sourceItem.onEntryClickListener?.onPopupMenuClicked(view, FileResponse(file.name, file.path))
                }
            }
        }


    }


}