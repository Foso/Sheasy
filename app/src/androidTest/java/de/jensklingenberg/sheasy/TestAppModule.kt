package de.jensklingenberg.sheasy

import android.app.Application
import de.jensklingenberg.sheasy.data.FileDataSource
import de.jensklingenberg.sheasy.data.file.AndroidFileRepository
import de.jensklingenberg.sheasy.data.preferences.SheasyPreferencesRepository
import de.jensklingenberg.sheasy.di.AppModule
import de.jensklingenberg.sheasy.network.SheasyPrefDataSource

class TestAppModule(
    app: App,
    val androidFileRepository: AndroidFileRepository,
    val testPreferences: SheasyPrefDataSource
) : AppModule(app) {


    override fun provideFileDataSource(): FileDataSource = androidFileRepository

    override fun provideSheasyPrefDataSource(application: Application): SheasyPrefDataSource = testPreferences
}