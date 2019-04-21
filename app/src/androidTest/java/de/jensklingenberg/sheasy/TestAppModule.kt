package de.jensklingenberg.sheasy

import de.jensklingenberg.sheasy.data.FileDataSource
import de.jensklingenberg.sheasy.data.file.AndroidFileRepository
import de.jensklingenberg.sheasy.di.AppModule

class TestAppModule(app:App,val androidFileRepository: AndroidFileRepository): AppModule(app) {


    override fun provideFileDataSource(): FileDataSource = androidFileRepository

}