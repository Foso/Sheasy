package de.jensklingenberg.sheasy.di

import dagger.Component
import de.jensklingenberg.sheasy.data.viewmodel.ViewModelFactory
import javax.inject.Singleton

@Component(modules = arrayOf( AppModule::class,RemoteModule::class))
@Singleton
interface AppComponent {

  fun inject(currencyViewModel: ViewModelFactory)


}
