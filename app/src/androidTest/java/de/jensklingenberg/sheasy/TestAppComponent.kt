package de.jensklingenberg.sheasy

import dagger.Component
import de.jensklingenberg.sheasy.di.AndroidModule
import de.jensklingenberg.sheasy.di.AppComponent
import de.jensklingenberg.sheasy.di.AppModule
import de.jensklingenberg.sheasy.di.NetworkModule
import de.jensklingenberg.sheasy.di.UseCaseModule
import javax.inject.Singleton

@Component(modules = [(AppModule::class), (UseCaseModule::class),(NetworkModule::class),(AndroidModule::class)])
@Singleton

open interface TestAppComponent  :AppComponent{
    fun inject(serverTest: ServerTest)



}