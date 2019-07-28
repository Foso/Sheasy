package de.jensklingenberg.sheasy.ui.files

import android.view.View
import de.jensklingenberg.sheasy.model.FileResponse
import de.jensklingenberg.sheasy.ui.common.BaseDataSourceItem
import java.io.File

class FolderSourceItem(
    fileResponse: File,
    val isFolder: Boolean,
    val imageId: Int,
    var onEntryClickListener: (view: View, fileResponse: FileResponse) -> Unit = { _, _ -> },
    var onContextMenuButtonClickedFunction: (view: View, fileResponse: FileResponse) -> Unit = { _, _ -> }

) :
    BaseDataSourceItem<File>(FolderViewHolder::class.java) {


    override fun areItemsTheSameInner(other: BaseDataSourceItem<File>): Boolean = false

    override fun areContentsTheSameInner(other: BaseDataSourceItem<File>): Boolean = false

    init {
        setPayload(fileResponse)
    }
}

