package de.jensklingenberg.sheasy.ui.files

import de.jensklingenberg.sheasy.model.FileResponse

interface FilesContract{
    interface Presenter{
        fun onCreate()
        fun loadFiles()
        fun folderUp()
    }
    interface View{
        fun updateFolderPathInfo(path: String)
        fun showPopup(popup: FileResponse?, view: android.view.View)
    }
}