package de.jensklingenberg.sheasy.ui.files

import de.jensklingenberg.sheasy.ui.common.BaseDataSourceItem
import de.jensklingenberg.sheasy.ui.common.OnEntryClickListener
import model.FileResponse

class FileResponseSourceItem(
    fileResponse: FileResponse,
    var onEntryClickListener: OnEntryClickListener? = null
) :
    BaseDataSourceItem<FileResponse>(FileResponseViewHolder::class.java) {


    override fun areItemsTheSameInner(other: BaseDataSourceItem<FileResponse>): Boolean {
        return false
    }

    override fun areContentsTheSameInner(other: BaseDataSourceItem<FileResponse>): Boolean {
        return false
    }


    init {
        setPayload(fileResponse)
    }


}