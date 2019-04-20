package de.jensklingenberg.sheasy.data.preferences

import android.app.Application
import android.os.Environment
import android.preference.PreferenceManager
import androidx.core.content.edit
import de.jensklingenberg.sheasy.R
import de.jensklingenberg.sheasy.data.devices.DevicesRepository
import de.jensklingenberg.sheasy.model.FileResponse
import de.jensklingenberg.sheasy.network.SheasyPrefDataSource
import io.reactivex.subjects.BehaviorSubject

class SheasyPreferencesRepository(val application: Application) : SheasyPrefDataSource {


    val sharedFoldersSubject: BehaviorSubject<List<FileResponse>> = BehaviorSubject.create<List<FileResponse>>()
    override val sharedFolders = arrayListOf<FileResponse>()
    override val nonInterceptedFolders: List<String> = listOf("/web/connection/")
    override val devicesRepository = DevicesRepository()
    override var appFolder = Environment.getExternalStorageDirectory().toString() + "/Sheasy/"
    override val httpPort = 8766
    override val webSocketPort = 8765
    override val defaultPath = Environment.getExternalStorageDirectory().toString()

    override fun addShareFolder(folder: FileResponse) {
        sharedFolders.add(folder)
        sharedFoldersSubject.onNext(sharedFolders)
    }

    override fun removeShareFolder(folder: FileResponse) {
        sharedFolders.remove(folder)
        sharedFoldersSubject.onNext(sharedFolders)
    }

    override var acceptAllConnections: Boolean
        get() = PreferenceManager.getDefaultSharedPreferences(application).getBoolean(
            application.getString(R.string.key_accept_all_connections),
            false
        )
        set(value) = PreferenceManager.getDefaultSharedPreferences(application).edit {
            putBoolean(application.getString(R.string.key_accept_all_connections), value)
            apply()
        }

    override fun observeSharedFolders(): BehaviorSubject<List<FileResponse>> {
        return sharedFoldersSubject

    }


}