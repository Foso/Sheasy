package de.jensklingenberg.rdrd.network

import io.reactivex.Single
import okhttp3.ResponseBody
import retrofit2.http.GET
import retrofit2.http.Streaming
import retrofit2.http.Url

/**
 * Created by jens on 11/2/18.
 */
interface Fdroid {

    @Streaming
    @GET
    abstract fun downloadPodcast(@Url url: String): Single<ResponseBody>


}