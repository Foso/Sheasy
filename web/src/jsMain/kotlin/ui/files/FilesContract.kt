package ui.files

import model.Error
import model.FileResponse
import ui.common.ReactPresenter

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