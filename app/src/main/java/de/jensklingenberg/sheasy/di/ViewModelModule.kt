package de.jensklingenberg.sheasy.di

import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProvider
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.multibindings.IntoMap
import de.jensklingenberg.sheasy.data.viewmodel.*
import javax.inject.Singleton

@Module
abstract class ViewModelModule {

    @Binds
    internal abstract fun bindViewModelFactory(factory: ViewModelFactory): ViewModelProvider.Factory

    @Binds
    @IntoMap
    @ViewModelKey(PermissionViewModel::class)
    internal abstract fun providePermissionViewModel(viewModel: PermissionViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(AppsViewModel::class)
    internal abstract fun provideAppsViewModel(viewModel: AppsViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(NetworkViewModel::class)
    internal abstract fun provideNetworkViewModel(viewModel: NetworkViewModel): ViewModel


    @Binds
    @IntoMap
    @ViewModelKey(CommonViewModel::class)
    internal abstract fun provideCommonViewModel(viewModel: CommonViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(ShareScreenViewModel::class)
    internal abstract fun provideShareScreenViewModel(viewModel: ShareScreenViewModel): ViewModel

    //Add more ViewModels here


}