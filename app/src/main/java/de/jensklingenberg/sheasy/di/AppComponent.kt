package de.jensklingenberg.sheasy.di

import dagger.Component
import de.jensklingenberg.sheasy.data.FileRepository
import de.jensklingenberg.sheasy.utils.NotificationUtils
import de.jensklingenberg.sheasy.network.HTTPServerService
import de.jensklingenberg.sheasy.ui.NewCommonViewModel
import de.jensklingenberg.sheasy.ui.ServerFragment
import javax.inject.Singleton

@Component(modules = [(AppModule::class), (UtilsModule::class), (ServiceModule::class)])
@Singleton
interface AppComponent {

    fun inject(commonViewModel: NewCommonViewModel)
    fun inject(httpServerService: HTTPServerService)
    fun inject(fileRepository: FileRepository)
    fun inject(serverFragment: ServerFragment)
    fun inject(notificationUtils: NotificationUtils)


}
