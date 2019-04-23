package de.jensklingenberg.sheasy.ui.files

import de.jensklingenberg.sheasy.model.FileResponse
import de.jensklingenberg.sheasy.ui.common.BaseDataSourceItem

class SharedFolderSourceItem(
    fileResponse: FileResponse,
    var onEntryClickListener: SharedFolderViewHolder.OnEntryClickListener? = null
) :
    BaseDataSourceItem<FileResponse>(SharedFolderViewHolder::class.java) {


    override fun areItemsTheSameInner(other: BaseDataSourceItem<FileResponse>): Boolean = false

    override fun areContentsTheSameInner(other: BaseDataSourceItem<FileResponse>): Boolean = false

    init {
        setPayload(fileResponse)
    }
}


