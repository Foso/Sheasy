package de.jensklingenberg.sheasy.ui.files

import de.jensklingenberg.sheasy.model.FileResponse
import de.jensklingenberg.sheasy.ui.common.BaseDataSourceItem
import de.jensklingenberg.sheasy.ui.common.MvpPresenter
import de.jensklingenberg.sheasy.ui.common.OnEntryClickListener
import java.io.File

interface FilesContract{
    interface Presenter:MvpPresenter, OnEntryClickListener{
        var filePath :String

        fun loadFiles()
        fun folderUp()
        fun share(file: File)
        fun hostFolder(item: FileResponse)
    }
    interface View{
        fun updateFolderPathInfo(path: String)
        fun showPopup(popup: FileResponse?, view: android.view.View)
        fun setData(list: List<BaseDataSourceItem<*>>)
        fun showError(it: Throwable)


    }
}