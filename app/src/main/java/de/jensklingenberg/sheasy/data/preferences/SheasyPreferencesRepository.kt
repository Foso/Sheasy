package de.jensklingenberg.sheasy.data.preferences

import android.app.Application
import android.os.Environment
import android.preference.PreferenceManager
import androidx.core.content.edit
import de.jensklingenberg.sheasy.R
import de.jensklingenberg.sheasy.data.devices.DevicesRepository
import de.jensklingenberg.sheasy.model.FileResponse
import de.jensklingenberg.sheasy.network.SheasyPrefDataSource

class SheasyPreferencesRepository(val application: Application) : SheasyPrefDataSource {
    override val nonInterceptedFolders: List<String> = listOf("/web/connection/")
    override val devicesRepository = DevicesRepository()


    override var appFolder = Environment.getExternalStorageDirectory().toString() + "/Sheasy/"

    override var acceptAllConnections: Boolean
        get() = PreferenceManager.getDefaultSharedPreferences(application).getBoolean(
            application.getString(R.string.key_accept_all_connections),
            false
        )
        set(value) = PreferenceManager.getDefaultSharedPreferences(application).edit {
            putBoolean(application.getString(R.string.key_accept_all_connections), value)
            apply()
        }

    override val sharedFolders = arrayListOf<FileResponse>()


    override val httpPort = 8766
    override val webSocketPort = 8765

    override val defaultPath = "/storage/emulated/0/"


}