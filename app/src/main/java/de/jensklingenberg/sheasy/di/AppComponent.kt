package de.jensklingenberg.sheasy.di

import android.arch.lifecycle.ViewModel
import dagger.Component
import de.jensklingenberg.sheasy.data.viewmodel.ViewModelFactory
import de.jensklingenberg.sheasy.ui.common.BaseFragment
import javax.inject.Singleton

@Component(modules = [(AppModule::class), (RemoteModule::class)])
@Singleton
interface AppComponent {

    fun inject(currencyViewModel: ViewModelFactory)
    fun inject(currencyViewModel: ViewModel) {

    }

    fun inject(baseFragment: BaseFragment) {

    }


}
