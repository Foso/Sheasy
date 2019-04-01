package de.jensklingenberg.sheasy.web.network

import de.jensklingenberg.sheasy.model.FileResponse
import de.jensklingenberg.sheasy.web.model.State
import de.jensklingenberg.sheasy.web.model.response.App
import de.jensklingenberg.sheasy.web.model.response.Resource
import kodando.rxjs.Observable
import org.w3c.files.File


interface API {
    fun getApps(): Observable<List<App>>
    fun getFiles(folderPath: String) :Observable<List<FileResponse>>
    fun uploadFile(file: File,folderPath: String):Observable<Resource<State>>
    fun getShared() : Observable<List<FileResponse>>
    fun downloadApk(packageName:String)
    fun downloadFile(path:String)

}
