package de.jensklingenberg.sheasy.web.ui.files

import de.jensklingenberg.sheasy.web.model.Error
import de.jensklingenberg.sheasy.web.model.File
import de.jensklingenberg.sheasy.web.model.response.FileResponse
import de.jensklingenberg.sheasy.web.ui.common.ReactPresenter

interface FilesContract {

    interface View {

        fun setData(filesResult: List<FileResponse>)
        fun showError(error: Error)

    }

    interface Presenter : ReactPresenter {

        fun setPath(path: String)
        fun getFiles()
        fun navigateUp()
        fun uploadFile(file: File)

    }

}