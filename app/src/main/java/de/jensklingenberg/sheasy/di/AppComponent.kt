package de.jensklingenberg.sheasy.di

import dagger.Component
import de.jensklingenberg.sheasy.data.event.EventRepository
import de.jensklingenberg.sheasy.data.file.AndroidFileRepository
import de.jensklingenberg.sheasy.data.notification.NotificationUtils
import de.jensklingenberg.sheasy.data.preferences.SheasyPreferencesRepository
import de.jensklingenberg.sheasy.service.HTTPServerService
import de.jensklingenberg.sheasy.network.Server
import de.jensklingenberg.sheasy.network.ktor.routehandler.AndroidFileRouteHandler
import de.jensklingenberg.sheasy.network.ktor.routehandler.AndroidKtorGeneralRouteHandler
import de.jensklingenberg.sheasy.service.NotificationListener
import de.jensklingenberg.sheasy.ui.MainActivity
import de.jensklingenberg.sheasy.ui.MainActivityDrawer
import de.jensklingenberg.sheasy.ui.about.AboutFragment
import de.jensklingenberg.sheasy.ui.about.AboutPresenter
import de.jensklingenberg.sheasy.ui.apps.AppInfoViewHolder
import de.jensklingenberg.sheasy.ui.apps.AppsFragment
import de.jensklingenberg.sheasy.ui.apps.AppsPresenter
import de.jensklingenberg.sheasy.ui.eventlog.EventLogFragment
import de.jensklingenberg.sheasy.ui.eventlog.EventLogPresenter
import de.jensklingenberg.sheasy.ui.files.FilesFragment
import de.jensklingenberg.sheasy.ui.files.FilesPresenter
import de.jensklingenberg.sheasy.ui.home.HomeFragment
import de.jensklingenberg.sheasy.ui.home.HomePresenter
import de.jensklingenberg.sheasy.ui.pairedDevices.PairedFragment
import de.jensklingenberg.sheasy.ui.pairedDevices.PairedPresenter
import de.jensklingenberg.sheasy.ui.settings.SettingsFragment
import de.jensklingenberg.sheasy.ui.settings.SettingsPresenter
import de.jensklingenberg.sheasy.ui.share.ShareFragment
import de.jensklingenberg.sheasy.ui.share.SharePresenter
import de.jensklingenberg.sheasy.data.usecase.ShareUseCase
import de.jensklingenberg.sheasy.network.ktor.routehandler.WebSocketRouteHandler
import javax.inject.Singleton

@Component(modules = [(AppModule::class), (UseCaseModule::class), (NetworkModule::class), (AndroidModule::class)])
@Singleton
interface AppComponent {

    fun inject(pairedPresenter: PairedPresenter)

    fun inject(httpServerService: HTTPServerService)
    fun inject(androidFileRepository: AndroidFileRepository)
    fun inject(aboutFragment: AboutFragment)
    fun inject(pairedFragment: PairedFragment)


    fun inject(settingsFragment: SettingsFragment)
    fun inject(notificationUtils: NotificationUtils)
    fun inject(appsFragment: AppsFragment)
    fun inject(homeFragment: HomeFragment)

    fun inject(server: Server)
    fun inject(filesFragment: FilesFragment)
    fun inject(shareUseCase: ShareUseCase)
    fun inject(androidKtorGeneralRouteHandler: AndroidKtorGeneralRouteHandler)
    fun inject(androidFileRouteHandler: AndroidFileRouteHandler)
    fun inject(appInfoViewHolder: AppInfoViewHolder)
    fun inject(filesPresenter: FilesPresenter)
    fun inject(aboutPresenter: AboutPresenter)
    fun inject(settingsPresenter: SettingsPresenter)
    fun inject(notificationListener: NotificationListener)
    fun inject(eventLogFragment: EventLogFragment)
    fun inject(eventLogPresenter: EventLogPresenter)
    fun inject(homePresenter: HomePresenter)
    fun inject(appsPresenter: AppsPresenter)
    fun inject(sharePresenter: SharePresenter)
    fun inject(shareFragment: ShareFragment)
    fun inject(eventRepository: EventRepository)
    fun inject(mainActivityDrawer: MainActivityDrawer)
    fun inject(mainActivity: MainActivity)
    fun inject(sheasyPreferencesRepository: SheasyPreferencesRepository)
    fun inject(webSocketRouteHandler: WebSocketRouteHandler)
}
