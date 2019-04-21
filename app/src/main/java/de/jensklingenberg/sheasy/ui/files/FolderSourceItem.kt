package de.jensklingenberg.sheasy.ui.files

import de.jensklingenberg.sheasy.model.FileResponse
import de.jensklingenberg.sheasy.ui.common.BaseDataSourceItem

class FolderSourceItem(
    fileResponse: FileResponse,
    var onEntryClickListener: FolderViewHolder.OnEntryClickListener? = null
) :
    BaseDataSourceItem<FileResponse>(FolderViewHolder::class.java) {


    override fun areItemsTheSameInner(other: BaseDataSourceItem<FileResponse>): Boolean = false

    override fun areContentsTheSameInner(other: BaseDataSourceItem<FileResponse>): Boolean = false

    init {
        setPayload(fileResponse)
    }
}

