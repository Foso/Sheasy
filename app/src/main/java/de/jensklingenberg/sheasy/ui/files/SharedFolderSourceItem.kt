package de.jensklingenberg.sheasy.ui.files

import android.view.View
import de.jensklingenberg.sheasy.model.FileResponse
import de.jensklingenberg.sheasy.ui.common.BaseDataSourceItem
import java.io.File

class SharedFolderSourceItem(
    file: FileResponse,
    val isFolder: Boolean,
    var onEntryClickListener: SharedFolderViewHolder.OnEntryClickListener? = null,
    var onContextMenuButtonClickedFunction: (view: View, fileResponse: File) -> Unit = { _, _ -> }

) :
    BaseDataSourceItem<FileResponse>(SharedFolderViewHolder::class.java) {


    override fun areItemsTheSameInner(other: BaseDataSourceItem<FileResponse>): Boolean = false

    override fun areContentsTheSameInner(other: BaseDataSourceItem<FileResponse>): Boolean = false

    init {
        setPayload(file)
    }
}


