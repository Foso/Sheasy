package de.jensklingenberg.sheasy.ui.files

import de.jensklingenberg.sheasy.ui.common.BaseDataSourceItem
import java.io.File

class FolderSourceItem(
    fileResponse: File,
    val isFolder: Boolean,
    var onEntryClickListener: FolderViewHolder.OnEntryClickListener? = null
) :
    BaseDataSourceItem<File>(FolderViewHolder::class.java) {


    override fun areItemsTheSameInner(other: BaseDataSourceItem<File>): Boolean = false

    override fun areContentsTheSameInner(other: BaseDataSourceItem<File>): Boolean = false

    init {
        setPayload(fileResponse)
    }
}

