package de.jensklingenberg.sheasy.legacy.utils.toplevel

import com.squareup.moshi.Moshi
import de.jensklingenberg.sheasy.legacy.utils.extension.toJson
import io.ktor.application.ApplicationCall
import io.ktor.http.ContentType
import io.ktor.response.respondText
import io.reactivex.Single
import io.reactivex.schedulers.Schedulers

/**
 * Created by jens on 30/3/18.
 */
fun runInBackground(function: () -> Unit) {
    Single.fromCallable {
        function()

        true
    }
        .subscribeOn(Schedulers.newThread())
        .observeOn(Schedulers.newThread())
        .subscribe { result ->
            //Use result for something
        }
}


suspend fun ApplicationCall.respondWithJSON(
    moshi: Moshi,
    appsResponseList2: List<Any>
) {
    val appsResponse =
        moshi.toJson(appsResponseList2)
    respondText(appsResponse, ContentType.Text.JavaScript)

}