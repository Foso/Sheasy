package de.jensklingenberg.sheasy.ui.screen

import android.hardware.display.DisplayManager
import android.media.projection.MediaProjection
import androidx.lifecycle.ViewModel


class ScreenViewModel : ViewModel() {


    private var mWidth: Int = 0
    private var mHeight: Int = 0


    companion object {

        private val TAG = ScreenCaptureFragment::class.java.name
        private val REQUEST_CODE = 100
        private var STORE_DIRECTORY: String? = null
        private var IMAGES_PRODUCED: Int = 0
        private val SCREENCAP_NAME = "screencap"
        private val VIRTUAL_DISPLAY_FLAGS =
            DisplayManager.VIRTUAL_DISPLAY_FLAG_OWN_CONTENT_ONLY or DisplayManager.VIRTUAL_DISPLAY_FLAG_PUBLIC
        private var sMediaProjection: MediaProjection? = null
    }


}
