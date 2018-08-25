package de.jensklingenberg.sheasy.data.viewmodel

import android.app.Application
import android.arch.lifecycle.AndroidViewModel
import android.preference.PreferenceManager
import androidx.core.content.edit
import de.jensklingenberg.sheasy.App


class SettingsViewModel(val application2: Application) : AndroidViewModel(application2) {


    companion object {


        fun savePort(application: Application, s: Int) {
            val preferences =
                PreferenceManager.getDefaultSharedPreferences(App.instance)
            val editor = preferences.edit {
                putInt("PORT", s)
                apply()
            }

        }


        fun loadPort(application: Application): Int {
            return PreferenceManager.getDefaultSharedPreferences(application).getInt("PORT", 8766)

        }

    }


}