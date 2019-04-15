package de.jensklingenberg.sheasy.di

import dagger.Component
import de.jensklingenberg.sheasy.data.event.EventRepository
import de.jensklingenberg.sheasy.data.file.FileRepository
import de.jensklingenberg.sheasy.network.HTTPServerService
import de.jensklingenberg.sheasy.network.Server
import de.jensklingenberg.sheasy.network.ktor.routehandler.AndroidFileRouteHandler
import de.jensklingenberg.sheasy.network.ktor.routehandler.AndroidKtorGeneralRouteHandler
import de.jensklingenberg.sheasy.network.websocket.websocket.MyWebSocket
import de.jensklingenberg.sheasy.network.websocket.websocket.NotificationWebSocket
import de.jensklingenberg.sheasy.network.websocket.websocket.ScreenShareWebSocket
import de.jensklingenberg.sheasy.network.websocket.websocket.ShareWebSocket
import de.jensklingenberg.sheasy.service.NotificationListener
import de.jensklingenberg.sheasy.ui.MainActivity
import de.jensklingenberg.sheasy.ui.MainActivityDrawer
import de.jensklingenberg.sheasy.ui.about.AboutFragment
import de.jensklingenberg.sheasy.ui.about.AboutPresenter
import de.jensklingenberg.sheasy.ui.apps.AppInfoViewHolder
import de.jensklingenberg.sheasy.ui.apps.AppsFragment
import de.jensklingenberg.sheasy.ui.apps.AppsPresenter
import de.jensklingenberg.sheasy.ui.common.OnResultActivity
import de.jensklingenberg.sheasy.ui.eventlog.EventLogFragment
import de.jensklingenberg.sheasy.ui.eventlog.EventLogPresenter
import de.jensklingenberg.sheasy.ui.files.FilesFragment
import de.jensklingenberg.sheasy.ui.files.FilesPresenter
import de.jensklingenberg.sheasy.ui.home.HomeFragment
import de.jensklingenberg.sheasy.ui.home.HomePresenter
import de.jensklingenberg.sheasy.ui.pairedDevices.PairedFragment
import de.jensklingenberg.sheasy.ui.pairedDevices.PairedPresenter
import de.jensklingenberg.sheasy.ui.screen.ScreenCaptureFragment
import de.jensklingenberg.sheasy.ui.settings.SettingsFragment
import de.jensklingenberg.sheasy.ui.settings.SettingsPresenter
import de.jensklingenberg.sheasy.ui.share.ShareFragment
import de.jensklingenberg.sheasy.ui.share.SharePresenter
import de.jensklingenberg.sheasy.data.notification.NotificationUtils
import de.jensklingenberg.sheasy.utils.ScreenRecord
import de.jensklingenberg.sheasy.utils.UseCase.ShareUseCase
import javax.inject.Singleton

@Component(modules = [(AppModule::class), (UseCaseModule::class), (NetworkModule::class), (AndroidModule::class)])
@Singleton
interface AppComponent {

    fun inject(pairedPresenter: PairedPresenter)

    fun inject(httpServerService: HTTPServerService)
    fun inject(fileRepository: FileRepository)
    fun inject(aboutFragment: AboutFragment)
    fun inject(pairedFragment: PairedFragment)
    fun inject(screenCaptureFragment: ScreenCaptureFragment)


    fun inject(settingsFragment: SettingsFragment)
    fun inject(notificationUtils: NotificationUtils)
    fun inject(appsFragment: AppsFragment)
    fun inject(homeFragment: HomeFragment)

    fun inject(myWebSocket: MyWebSocket)
    fun inject(server: Server)
    fun inject(screenShareWebSocket: ScreenShareWebSocket)
    fun inject(screenRecord: ScreenRecord)
    fun inject(onResultActivity: OnResultActivity)
    fun inject(filesFragment: FilesFragment)
    fun inject(shareUseCase: ShareUseCase)
    fun inject(androidKtorGeneralRouteHandler: AndroidKtorGeneralRouteHandler)
    fun inject(androidFileRouteHandler: AndroidFileRouteHandler)
    fun inject(appInfoViewHolder: AppInfoViewHolder)
    fun inject(filesPresenter: FilesPresenter)
    fun inject(aboutPresenter: AboutPresenter)
    fun inject(settingsPresenter: SettingsPresenter)
    fun inject(notificationListener: NotificationListener)
    fun inject(notificationWebSocket: NotificationWebSocket)
    fun inject(eventLogFragment: EventLogFragment)
    fun inject(eventLogPresenter: EventLogPresenter)
    fun inject(homePresenter: HomePresenter)
    fun inject(appsPresenter: AppsPresenter)
    fun inject(shareWebSocket: ShareWebSocket)
    fun inject(sharePresenter: SharePresenter)
    fun inject(shareFragment: ShareFragment)
    fun inject(eventRepository: EventRepository)
    fun inject(mainActivityDrawer: MainActivityDrawer)
    fun inject(mainActivity: MainActivity)

}
