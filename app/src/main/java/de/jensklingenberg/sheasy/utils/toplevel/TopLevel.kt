package de.jensklingenberg.sheasy.utils.toplevel

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
