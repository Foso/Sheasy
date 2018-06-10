package de.jensklingenberg.sheasy.data.viewmodel

import android.app.Application
import android.arch.lifecycle.AndroidViewModel
import android.arch.lifecycle.MutableLiveData
import android.content.Context
import android.preference.PreferenceManager
import android.support.v7.widget.PopupMenu
import android.view.MenuItem
import android.view.View
import androidx.core.content.edit
import de.jensklingenberg.sheasy.App
import de.jensklingenberg.sheasy.R
import de.jensklingenberg.sheasy.model.AppsResponse
import de.jensklingenberg.sheasy.model.SingleLiveEvent
import de.jensklingenberg.sheasy.utils.AppUtils
import de.jensklingenberg.sheasy.utils.FUtils
import java.io.File


class AppsViewModel(val application2: Application) : AndroidViewModel(application2) {

    var apps: MutableLiveData<List<AppsResponse>> = MutableLiveData()

    var clickedMenuItem: SingleLiveEvent<MenuItem> = SingleLiveEvent()


    fun showPopup(context: Context?, v: View) {
        context?.let {
            PopupMenu(context, v).apply {
                inflate(R.menu.apps_actions)
                show()
                setOnMenuItemClickListener {
                    clickedMenuItem.value = it
                    true
                }
            }
        }

    }

    fun extractApk(context: Context, packageName: String) {


        val test = FUtils.returnAPK(context, packageName)
        val mimeType = test?.mimeType
        val fileInputStream = test?.fileInputStream
        File()

    }

    fun getApps() {
        apps.value = AppUtils.getAppsResponseList(application2)
    }

}