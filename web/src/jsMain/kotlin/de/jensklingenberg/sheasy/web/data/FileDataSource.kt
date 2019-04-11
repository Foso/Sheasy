package de.jensklingenberg.sheasy.web.data

import de.jensklingenberg.sheasy.model.FileResponse
import de.jensklingenberg.sheasy.model.Resource
import de.jensklingenberg.sheasy.web.model.State
import de.jensklingenberg.sheasy.web.model.response.App
import kodando.rxjs.Observable
import org.w3c.files.File


interface FileDataSource{
    fun getShared(): Observable<List<FileResponse>>
    fun getApps(): Observable<List<App>>
    fun getFiles(folderPath: String): Observable<List<FileResponse>>
    fun uploadFile(file: File,folderPath: String): Observable<Resource<State>>
    fun downloadApk(app:App?)
    fun downloadFile(fileResponse: FileResponse)
}