package de.jensklingenberg.sheasy.data.viewmodel

import android.app.Application
import android.arch.lifecycle.AndroidViewModel
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import android.content.Context
import android.os.Environment
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
import de.jensklingenberg.sheasy.utils.ResponseFile
import java.io.File

import javax.inject.Inject


class AppsViewModel @Inject constructor() : ViewModel() {

    @Inject
    lateinit var application: Application


    var apps: MutableLiveData<List<AppsResponse>> = MutableLiveData()

    var clickedMenuItem: SingleLiveEvent<MenuItem> = SingleLiveEvent()
    var toastMessage: SingleLiveEvent<Int> = SingleLiveEvent()


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

        when (AppUtils.extractApk(context, packageName)) {
            true -> {
                toastMessage.value = R.string.Success
            }
            false -> {
                toastMessage.value = R.string.Failed
            }
        }


    }

    fun getApps() {
        apps.value = AppUtils.getAppsResponseList(application)
    }

}