package de.jensklingenberg.sheasy.ui.common

import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable

interface MvpPresenter {
    val compositeDisposable: CompositeDisposable

    fun onCreate()
    fun onDestroy()
}

fun Disposable.addTo(disposable: CompositeDisposable) {
    disposable.add(this)
}