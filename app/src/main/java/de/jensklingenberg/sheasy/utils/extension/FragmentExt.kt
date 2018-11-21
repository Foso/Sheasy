package de.jensklingenberg.sheasy.utils.extension

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProviders

inline fun <T : ViewModel> Fragment.obtainViewModel(java: Class<T>): T {
    return ViewModelProviders.of(this).get(java)

}

inline fun <T : ViewModel> FragmentActivity.obtainViewModel(java: Class<T>): T {
    return ViewModelProviders.of(this).get(java)

}