package de.jensklingenberg.sheasy.utils.toplevel

import android.view.View
import de.jensklingenberg.sheasy.utils.extension.setVisible
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

fun setViewVisible(view: View) {
    view.visibility = View.VISIBLE
}

fun setViewInvisible(view: View) {
    view.visibility = View.INVISIBLE
}

fun setViewGone(view: View) {
    view.visibility = View.INVISIBLE
}


