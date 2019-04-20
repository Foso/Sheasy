package de.jensklingenberg.sheasy.ui.files

import de.jensklingenberg.sheasy.model.FileResponse
import de.jensklingenberg.sheasy.ui.common.BaseDataSourceItem
import de.jensklingenberg.sheasy.ui.common.MvpPresenter
import java.io.File

interface FilesContract {
    interface Presenter : MvpPresenter, SharedFolderViewHolder.OnEntryClickListener,
        FileResponseViewHolder.OnEntryClickListener {
        var filePath: String

        fun loadFiles()
        fun folderUp()
        fun share(file: File)
        fun hostFolder(item: FileResponse)
        fun searchFile(fileName: String)
    }

    interface View {
        fun updateFolderPathInfo(path: String)
        fun setData(list: List<BaseDataSourceItem<*>>)
        fun showError(it: Throwable)


    }
}