package de.jensklingenberg.sheasy.ui.files

import de.jensklingenberg.sheasy.ui.common.BaseDataSourceItem
import java.io.File

class SharedFolderSourceItem(
    file: File,
    val isFolder: Boolean,
    var onEntryClickListener: SharedFolderViewHolder.OnEntryClickListener? = null
) :
    BaseDataSourceItem<File>(SharedFolderViewHolder::class.java) {


    override fun areItemsTheSameInner(other: BaseDataSourceItem<File>): Boolean = false

    override fun areContentsTheSameInner(other: BaseDataSourceItem<File>): Boolean = false

    init {
        setPayload(file)
    }
}


