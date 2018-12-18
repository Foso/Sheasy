package de.jensklingenberg.sheasy.di

import dagger.Component
import de.jensklingenberg.sheasy.data.file.FileRepository
import de.jensklingenberg.sheasy.network.*
import de.jensklingenberg.sheasy.ui.MainViewModel
import de.jensklingenberg.sheasy.ui.about.AboutFragment
import de.jensklingenberg.sheasy.ui.about.AboutViewModel
import de.jensklingenberg.sheasy.ui.apps.AppsFragment
import de.jensklingenberg.sheasy.ui.apps.AppsViewModel
import de.jensklingenberg.sheasy.ui.common.BaseFragment
import de.jensklingenberg.sheasy.ui.files.FilesViewModel
import de.jensklingenberg.sheasy.ui.home.HomeFragment
import de.jensklingenberg.sheasy.ui.pairedDevices.PairedViewModel
import de.jensklingenberg.sheasy.ui.settings.ScreenCaptureImage
import de.jensklingenberg.sheasy.ui.settings.SettingsFragment
import de.jensklingenberg.sheasy.utils.NotificationUtils
import de.jensklingenberg.sheasy.utils.ScreenRecord
import javax.inject.Singleton

@Component(modules = [(AppModule::class), (UtilsModule::class), (ServiceModule::class)])
@Singleton
interface AppComponent {

    fun inject(commonViewModel: MainViewModel)
    fun inject(appsViewModel: AppsViewModel)
    fun inject(pairedViewModel: PairedViewModel)

    fun inject(httpServerService: HTTPServerService)
    fun inject(fileRepository: FileRepository)
    fun inject(aboutFragment: AboutFragment)
    fun inject(baseFragment: BaseFragment)
    fun inject(screenCaptureImage: ScreenCaptureImage)


    fun inject(settingsFragment: SettingsFragment)
    fun inject(notificationUtils: NotificationUtils)
    fun inject(appsFragment: AppsFragment)
    fun inject(homeFragment: HomeFragment)
    fun inject(aboutViewModel: AboutViewModel)
    fun inject(filesViewModel: FilesViewModel)
    fun inject(myWebSocket: MyWebSocket)
    fun inject(server: Server)
    fun inject(screenShareWebSocket: ScreenShareWebSocket)
    fun inject(screenRecord: ScreenRecord)
    fun inject(onResultActivity: OnResultActivity)

}
