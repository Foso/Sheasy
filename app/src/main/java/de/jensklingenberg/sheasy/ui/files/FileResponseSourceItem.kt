package de.jensklingenberg.sheasy.ui.files

import de.jensklingenberg.sheasy.model.FileResponse
import de.jensklingenberg.sheasy.ui.common.BaseDataSourceItem
import de.jensklingenberg.sheasy.ui.common.OnEntryClickListener

class FileResponseSourceItem(
    fileResponse: FileResponse,
    var onEntryClickListener: FileResponseViewHolder.OnEntryClickListener? = null
) :
    BaseDataSourceItem<FileResponse>(FileResponseViewHolder::class.java) {


    override fun areItemsTheSameInner(other: BaseDataSourceItem<FileResponse>): Boolean = false

    override fun areContentsTheSameInner(other: BaseDataSourceItem<FileResponse>): Boolean = false

    init {
        setPayload(fileResponse)
    }
}

fun FileResponse.toFileResponseSourceItem(onEntryClickListener: FileResponseViewHolder.OnEntryClickListener? = null): FileResponseSourceItem {
    return FileResponseSourceItem(this, onEntryClickListener)
}