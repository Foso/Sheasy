package de.jensklingenberg.sheasy.di

import dagger.Component
import de.jensklingenberg.sheasy.data.FileRepository
import de.jensklingenberg.sheasy.network.HTTPServerService
import de.jensklingenberg.sheasy.ui.HomeFragment
import de.jensklingenberg.sheasy.ui.MainViewModel
import de.jensklingenberg.sheasy.ui.about.AboutFragment
import de.jensklingenberg.sheasy.ui.about.AboutViewModel
import de.jensklingenberg.sheasy.ui.apps.AppsFragment
import de.jensklingenberg.sheasy.ui.apps.AppsViewModel
import de.jensklingenberg.sheasy.ui.settings.SettingsFragment
import de.jensklingenberg.sheasy.utils.NotificationUtils
import javax.inject.Singleton

@Component(modules = [(AppModule::class), (UtilsModule::class), (ServiceModule::class)])
@Singleton
interface AppComponent {

    fun inject(commonViewModel: MainViewModel)
    fun inject(appsViewModel: AppsViewModel)

    fun inject(httpServerService: HTTPServerService)
    fun inject(fileRepository: FileRepository)
    fun inject(aboutFragment: AboutFragment)

    fun inject(settingsFragment: SettingsFragment)
    fun inject(notificationUtils: NotificationUtils)
    fun inject(appsFragment: AppsFragment)
    fun inject(homeFragment: HomeFragment)
    fun inject(aboutViewModel: AboutViewModel)


}
