package de.jensklingenberg.sheasy.ui.files

import de.jensklingenberg.sheasy.ui.common.BaseDataSourceItem
import de.jensklingenberg.sheasy.ui.common.OnEntryClickListener
import de.jensklingenberg.sheasy.web.model.FileResponse

class FileResponseSourceItem(
    fileResponse: FileResponse,
    var onEntryClickListener: OnEntryClickListener? = null
) :
    BaseDataSourceItem<FileResponse>(FileResponseViewHolder::class.java) {


    override fun areItemsTheSameInner(other: BaseDataSourceItem<FileResponse>): Boolean = false

    override fun areContentsTheSameInner(other: BaseDataSourceItem<FileResponse>): Boolean = false

    init {
        setPayload(fileResponse)
    }
}