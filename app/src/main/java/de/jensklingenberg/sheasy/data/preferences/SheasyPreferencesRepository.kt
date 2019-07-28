package de.jensklingenberg.sheasy.data.preferences

import android.app.Application
import android.os.Environment
import android.preference.PreferenceManager
import androidx.core.content.edit
import de.jensklingenberg.sheasy.App
import de.jensklingenberg.sheasy.R
import de.jensklingenberg.sheasy.data.event.EventDataSource
import de.jensklingenberg.sheasy.data.usecase.GetIpUseCase
import de.jensklingenberg.sheasy.model.FileResponse
import de.jensklingenberg.sheasy.model.RefreshClientEvent
import de.jensklingenberg.sheasy.network.SheasyPrefDataSource
import io.reactivex.subjects.BehaviorSubject
import network.SharedNetworkSettings
import javax.inject.Inject

class SheasyPreferencesRepository : SheasyPrefDataSource {


    @Inject
    lateinit var getIpUseCase: GetIpUseCase

    @Inject
    lateinit var application: Application

    @Inject
    lateinit var eventDataSource: EventDataSource

    init {
        initializeDagger()
    }

    private fun initializeDagger() = App.appComponent.inject(this)


    private val sharedFoldersSubject: BehaviorSubject<List<FileResponse>> = BehaviorSubject.create()
    override val sharedFolders = arrayListOf<FileResponse>()
    override val nonInterceptedFolders: List<String> = listOf("/web/connection/")
    override var appFolder = Environment.getExternalStorageDirectory().toString() + "/Sheasy/"
    override val httpPort = SharedNetworkSettings.httpPort
    override val webSocketPort = 8765
    override val defaultPath = Environment.getExternalStorageDirectory().toString()

    override fun addShareFolder(folder: FileResponse) {
        if (!sharedFolders.contains(folder)) {
            eventDataSource.addClientEvent(RefreshClientEvent())
            sharedFolders.add(folder)
            sharedFoldersSubject.onNext(sharedFolders)
        }

    }

    override fun removeShareFolder(folder: FileResponse) {
        eventDataSource.addClientEvent(RefreshClientEvent())

        sharedFolders.remove(folder)
        sharedFoldersSubject.onNext(sharedFolders)
    }

    override fun removeAllSharedFolder() {
        eventDataSource.addClientEvent(RefreshClientEvent())

        sharedFolders.clear()
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

    override fun observeSharedFolders(): BehaviorSubject<List<FileResponse>> = sharedFoldersSubject

    override fun getBaseUrl(): String = "http://" + getIpUseCase.getIP() + ":" + httpPort + "/api/v1/"


}