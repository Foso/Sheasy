package de.jensklingenberg.sheasy.ui.files

import android.os.Bundle
import android.view.View
import android.view.ViewGroup
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
                    item2.onContextMenuButtonClickedFunction(it, FileResponse(fileResponse.name, fileResponse.path))
                }
            }
        }


    }


}