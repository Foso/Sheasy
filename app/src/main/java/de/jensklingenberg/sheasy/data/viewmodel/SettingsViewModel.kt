package de.jensklingenberg.sheasy.data.viewmodel

import android.app.Application
import android.arch.lifecycle.AndroidViewModel
import android.R.id.edit
import android.content.SharedPreferences
import android.preference.PreferenceManager
import de.jensklingenberg.sheasy.App


class SettingsViewModel(val application2: Application) : AndroidViewModel(application2){


    companion object {


        fun savePort(application: Application,s: Int) {
            val preferences =
                PreferenceManager.getDefaultSharedPreferences(App.instance)
            val editor = preferences.edit()
            editor.putInt("PORT", s)
            editor.apply()
        }


        fun loadPort(application: Application): Int {
            val preferences =
                PreferenceManager.getDefaultSharedPreferences(application)
            val name = preferences.getInt("PORT", 8766)
           return name

        }

    }



}