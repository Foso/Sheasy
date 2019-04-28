package de.jensklingenberg.sheasy.ui.files

import de.jensklingenberg.sheasy.ui.common.BaseDataSourceItem
import java.io.File

class FileSourceItem(
    File: File,
    var onEntryClickListener: FileViewHolder.OnEntryClickListener? = null
) :
    BaseDataSourceItem<File>(FileViewHolder::class.java) {


    override fun areItemsTheSameInner(other: BaseDataSourceItem<File>): Boolean = false

    override fun areContentsTheSameInner(other: BaseDataSourceItem<File>): Boolean = false

    init {
        setPayload(File)
    }
}

