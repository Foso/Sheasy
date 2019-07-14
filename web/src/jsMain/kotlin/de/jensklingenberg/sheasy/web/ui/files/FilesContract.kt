package de.jensklingenberg.sheasy.web.ui.files

import de.jensklingenberg.sheasy.model.FileResponse
import de.jensklingenberg.sheasy.model.SheasyError
import de.jensklingenberg.sheasy.web.model.SourceItem
import de.jensklingenberg.sheasy.web.ui.common.ReactPresenter
import org.w3c.dom.events.Event
import org.w3c.files.File

interface FilesContract {

    interface View {

        fun showError(error: SheasyError)
        fun showSnackBar(message: String)
        fun setData(items: List<SourceItem>)

        fun handleClickListItem(event: Event, fileResponse: FileResponse)
        fun setContextMenuVisibility(visibility: Boolean)
    }

    interface Presenter : ReactPresenter {
        fun setPath(path: String)
        fun getFiles()
        fun navigateUp()
        fun uploadFile(file: File)
        fun getShared()
        fun getFile(fileResponse: FileResponse?)
        fun onSearch(query: String)
    }

}