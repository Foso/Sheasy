package de.jensklingenberg.sheasy.utils.toplevel

import android.annotation.SuppressLint
import io.reactivex.Single
import io.reactivex.schedulers.Schedulers

/**
 * Created by jens on 30/3/18.
 */
@SuppressLint("CheckResult")
fun runInBackground(function: () -> Unit) {
    Single.fromCallable {
        function()

        true
    }
        .subscribeOn(Schedulers.newThread())
        .observeOn(Schedulers.newThread())
        .subscribe { _ ->
            //Use result for something
        }
}
