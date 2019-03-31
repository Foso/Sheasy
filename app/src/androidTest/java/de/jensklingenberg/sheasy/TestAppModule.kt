package de.jensklingenberg.sheasy

import de.jensklingenberg.sheasy.data.file.FileRepository
import de.jensklingenberg.sheasy.di.AppModule

class TestAppModule(app:App,val fileRepository: FileRepository): AppModule(app) {


    override fun provideFileRepository(): FileDataSource = fileRepository



}