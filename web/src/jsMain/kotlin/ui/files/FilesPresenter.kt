package ui.files

import kotlinext.js.jsObject
import model.Error
import model.FileResponse
import network.Axios
import network.NetworkUtil

class FilesPresenter(val view: FilesContract.View) : FilesContract.Presenter {


    val defaultPath = "/storage/emulated/0/Music"
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
        Axios.get<Array<FileResponse>>(NetworkUtil.getFiles(path), jsObject {
            timeout = 10000
        }).then { result ->
            filesResult = result.data.toMutableList()
            view.setData(filesResult)
        }.catch { error ->
            view.showError(Error.NETWORK_ERROR)
            console.log(error)
        }
    }

}