package de.jensklingenberg.sheasy.data.viewmodel

import android.app.Activity
import android.app.Application
import android.arch.lifecycle.AndroidViewModel
import android.preference.PreferenceManager
import de.jensklingenberg.sheasy.App
import de.jensklingenberg.sheasy.utils.extension.mediaProjectionManager
import javax.inject.Inject


class ShareScreenViewModel @Inject constructor(val application2: Application) :
    AndroidViewModel(application2) {
    fun requestScreenRecord(activity: Activity, request: Int) {
        val permissionIntent =
            activity.mediaProjectionManager().createScreenCaptureIntent()
        activity.startActivityForResult(permissionIntent, request)

    }


}