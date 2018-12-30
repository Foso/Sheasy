package de.jensklingenberg.react.ui.files

import de.jensklingenberg.model.Error
import de.jensklingenberg.model.response.FileResponse
import de.jensklingenberg.react.ui.common.ReactPresenter

interface FilesContract {

    interface View {

        fun setData(filesResult: List<FileResponse>)
        fun showError(error: Error)

    }

    interface Presenter : ReactPresenter {

        fun setPath(path: String)
        fun getFiles()
        fun navigateUp()

    }

}