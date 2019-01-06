package de.jensklingenberg.sheasy.utils.extension

import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProviders

fun <T : ViewModel> Fragment.obtainViewModel(java: Class<T>): T {
    return ViewModelProviders.of(this).get(java)

}

inline fun <T : ViewModel> FragmentActivity.obtainViewModel(java: Class<T>): T {
    return ViewModelProviders.of(this).get(java)

}

fun Fragment.requireView(): View {
    when(val view = view){
        null->throw IllegalStateException("View " + this + " not attached to an fragment.")
        else->return view
    }
}