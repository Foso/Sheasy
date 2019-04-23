package de.jensklingenberg.sheasy.network

import de.jensklingenberg.sheasy.model.AppInfo
import de.jensklingenberg.sheasy.model.FileResponse
import de.jensklingenberg.sheasy.model.Resource
import io.reactivex.Single
import retrofit2.Response
import retrofit2.http.GET

interface SheasyApi {


    @GET("api/v1/file/shared")
    fun getShared(): Single<Response<Resource<List<FileResponse>>>>

    @GET("api/v1/file/apps")
    fun getApps(): Single<Response<Resource<List<AppInfo>>>>

}