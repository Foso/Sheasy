package de.jensklingenberg.sheasy

import android.app.Application
import de.jensklingenberg.sheasy.data.file.FileDataSource
import de.jensklingenberg.sheasy.data.file.FileRepository
import de.jensklingenberg.sheasy.di.AppModule
import io.mockk.spyk

class TestAppModule(app:App,val fileRepository: FileRepository): AppModule(app) {


    override fun provideFileRepository(): FileDataSource = fileRepository



}