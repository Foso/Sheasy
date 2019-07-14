package de.jensklingenberg.sheasy.utils.extension

import android.view.View
import androidx.fragment.app.Fragment


fun Fragment.requireView(): View {
    when (val view = view) {
        null -> throw IllegalStateException("View " + this + " not attached to an fragment.")
        else -> return view
    }
}