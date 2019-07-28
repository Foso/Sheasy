package de.jensklingenberg.sheasy.ui.files

import android.os.Bundle
import android.view.View.VISIBLE
import android.view.ViewGroup
import de.jensklingenberg.sheasy.R
import de.jensklingenberg.sheasy.model.FileResponse
import de.jensklingenberg.sheasy.ui.common.BaseViewHolder
import kotlinx.android.synthetic.main.list_item_generic.view.*

class FolderViewHolder(viewParent: ViewGroup) :
    BaseViewHolder<FileSourceItem>(viewParent, R.layout.list_item_generic) {


    override fun onBindViewHolder(sourceItem: Any, diff: Bundle) {

        val file = (sourceItem as FolderSourceItem).getPayload()

        file?.let {
            itemView.apply {
                title.text = file.name
                caption.text = file.path
                moreBtn.visibility = VISIBLE

                icon.setImageResource(sourceItem.imageId)
                moreBtn.setOnClickListener {
                    sourceItem.onContextMenuButtonClickedFunction(it, FileResponse(file.name, file.path))


                }


                item.setOnClickListener {
                    sourceItem.onEntryClickListener(it, FileResponse(file.name, file.path))

                }


            }
        }


    }


}