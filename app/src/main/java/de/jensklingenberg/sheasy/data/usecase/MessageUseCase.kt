package de.jensklingenberg.sheasy.data.usecase

import android.view.View
import com.google.android.material.snackbar.Snackbar

class MessageUseCase {


    fun show(view: View, message: String) {
        Snackbar.make(view, message, Snackbar.LENGTH_SHORT)
            .setAction("CLOSE") { }
            // .setActionTextColor(resources.getColor(android.R.color.holo_red_light))
            .show()
    }
}