package de.jensklingenberg.sheasy.web.ui.files

import de.jensklingenberg.sheasy.web.model.Error
import de.jensklingenberg.sheasy.web.model.response.FileResponse
import de.jensklingenberg.sheasy.web.ui.common.ReactPresenter
import org.w3c.files.File

interface FilesContract {

    interface View {

        fun setData(filesResult: List<FileResponse>)
        fun showError(error: Error)
        fun showSnackBar(message:String)

    }

    interface Presenter : ReactPresenter {

        fun setPath(path: String)
        fun getFiles()
        fun navigateUp()
        fun uploadFile(file: File)
        fun getShared()

    }

}