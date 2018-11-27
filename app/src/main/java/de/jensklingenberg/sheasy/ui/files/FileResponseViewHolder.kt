package de.jensklingenberg.sheasy.ui.files

import android.os.Bundle
import android.view.ViewGroup
import de.jensklingenberg.sheasy.R
import de.jensklingenberg.sheasy.ui.common.BaseViewHolder
import kotlinx.android.synthetic.main.list_item_generic.view.*

class FileResponseViewHolder(viewParent: ViewGroup) :
    BaseViewHolder<FileResponseSourceItem>(viewParent, R.layout.list_item_generic) {


    override fun onBindViewHolder(item2: Any, diff: Bundle) {

        val fileResponse = (item2 as FileResponseSourceItem).getPayload()

        fileResponse?.let {
            itemView.apply {
                title.text = fileResponse.name
                caption.text = fileResponse.path
                icon.setImageResource(R.drawable.ic_folder_grey_700_24dp)
                setOnClickListener {
                    item2.onEntryClickListener?.onItemClicked(fileResponse)
                }
            }
        }


    }


}