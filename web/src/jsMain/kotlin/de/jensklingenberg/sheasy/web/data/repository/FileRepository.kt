package de.jensklingenberg.sheasy.web.data.repository

import de.jensklingenberg.sheasy.model.FileResponse
import de.jensklingenberg.sheasy.web.data.FileDataSource
import de.jensklingenberg.sheasy.web.model.State
import de.jensklingenberg.sheasy.web.model.response.App
import de.jensklingenberg.sheasy.model.Resource
import de.jensklingenberg.sheasy.web.network.API
import kodando.rxjs.Observable
import org.w3c.files.File


class FileRepository(val api: API) : FileDataSource {
    override fun downloadFile(fileResponse: FileResponse) {
            api.downloadFile(fileResponse.path)
    }

    override fun downloadApk(app: App?) {
        app?.let{
          api.downloadApk(app.packageName)
        }
    }

    override fun uploadFile(file: File,folderPath: String): Observable<Resource<State>> {
        return api.uploadFile(file,folderPath)

    }

    override fun getShared(): Observable<List<FileResponse>> {
       return api.getShared()
    }

    override fun getFiles(folderPath: String): Observable<List<FileResponse>> {
       return  api.getFiles(folderPath)
    }

    override fun getApps(): Observable<List<App>> {
      return api.getApps()
    }


}