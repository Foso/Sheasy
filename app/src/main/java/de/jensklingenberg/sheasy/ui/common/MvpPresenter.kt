package de.jensklingenberg.sheasy.ui.common

import io.reactivex.disposables.CompositeDisposable

interface MvpPresenter{
    val compositeDisposable: CompositeDisposable

    fun onCreate()
    fun onDestroy()
}