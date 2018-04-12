package de.jensklingenberg.sheasy.toplevel

import com.squareup.moshi.Moshi
import de.jensklingenberg.sheasy.model.NotificationResponse
import io.reactivex.Observable
import io.reactivex.schedulers.Schedulers

/**
 * Created by jens on 30/3/18.
 */
public fun runInBackground(function: () -> Unit) {
    Observable.fromCallable {
        function()

        true
    }
            .subscribeOn(Schedulers.newThread())
            .observeOn(Schedulers.newThread())
            .subscribe { result ->
                //Use result for something
            }
}