package de.jensklingenberg.sheasy.web.usecase

import components.materialui.Snackbar
import components.materialui.SnackbarOrigin
import react.RBuilder

class MessageUseCase {


    fun showErrorSnackbar(rBuilder: RBuilder, snackbarMessage: String, showSnackbar: Boolean) {
        rBuilder.run {

            Snackbar {
                attrs {
                    this.anchorOrigin = object : SnackbarOrigin {
                        override var horizontal: String? = "center"
                        override var vertical: String? = "bottom"
                    }
                    open = showSnackbar
                    message = snackbarMessage
                    autoHideDuration = 6000
                    onClose = { false }
                    variant = "error"
                }
            }
        }
    }


}