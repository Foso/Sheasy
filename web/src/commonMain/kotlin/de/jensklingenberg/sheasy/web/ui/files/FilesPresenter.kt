package de.jensklingenberg.sheasy.web.ui.files

import de.jensklingenberg.sheasy.web.model.Error
import de.jensklingenberg.sheasy.web.model.response.FileResponse
import de.jensklingenberg.sheasy.web.data.AppsDataSource

class FilesPresenter(val view: FilesContract.View,val appsDataSource: AppsDataSource) : FilesContract.Presenter {
    val defaultPath = "/"
    var path = defaultPath
    var filesResult = listOf<FileResponse>()

    /****************************************** React Lifecycle methods  */
    override fun componentWillUnmount() {}

    override fun componentDidMount() {}

    /****************************************** Presenter methods  */
    override fun navigateUp() {
        path = path.replaceAfterLast("/", "")
        getFiles()
    }

    override fun setPath(path: String) {
        this.path = path
        getFiles()
    }

    override fun getFiles() {
        appsDataSource.getFiles(
            folderPath = path,
            onSuccess = {
                filesResult=it
                view.setData(it)
            },
            onError = {
                view.showError(Error.NETWORK_ERROR)
            }
        )
    }
}