import kotlinext.js.requireAll
import org.kodein.di.Kodein
import org.kodein.di.KodeinAware
import react.dom.render
import de.jensklingenberg.sheasy.web.components.router.hashRouter
import de.jensklingenberg.sheasy.web.components.router.route
import de.jensklingenberg.sheasy.web.components.router.switch
import de.jensklingenberg.sheasy.web.data.FileDataSource
import de.jensklingenberg.sheasy.web.data.NetworkPreferences
import de.jensklingenberg.sheasy.web.data.repository.FileRepository
import de.jensklingenberg.sheasy.web.model.Route
import de.jensklingenberg.sheasy.web.network.ReactHttpClient
import de.jensklingenberg.sheasy.web.ui.home.HomeView
import de.jensklingenberg.sheasy.web.ui.about.AboutView
import de.jensklingenberg.sheasy.web.di.DaggerAppComponent
import de.jensklingenberg.sheasy.web.ui.about.AppComponent
import de.jensklingenberg.sheasy.web.ui.apps.AppsView
import de.jensklingenberg.sheasy.web.ui.files.FileView
import de.jensklingenberg.sheasy.web.ui.screenshare.ScreenShareView
import de.jensklingenberg.sheasy.web.usecase.NotificationUseCase
import org.kodein.di.erased.bind
import org.kodein.di.erased.singleton
import kotlin.browser.document
import kotlin.browser.window


class Application : KodeinAware {


    companion object {
        val appComponent:AppComponent= DaggerAppComponent()
        lateinit var kode : Kodein
    }

    val routeList = listOf(
        Route("/", HomeView::class, true),
        Route("/apps", AppsView::class, exact = true),
        Route("/files", FileView::class, exact = true),
        Route("/about", AboutView::class, exact = true),
        Route("/screenshare", ScreenShareView::class, exact = true)

    )

    override val kodein = Kodein {
            bind<FileDataSource>() with singleton { FileRepository(ReactHttpClient(NetworkPreferences())) }
            bind<NotificationUseCase>() with singleton { NotificationUseCase() }
    }

    init {
        kode=kodein
        window.onload = {
            kotlinext.js.require("bootstrap/dist/css/bootstrap.min.css")
            requireAll(kotlinext.js.require.context("kotlin", true, js("/\\.css$/")))

            render(document.getElementById("root")) {


                hashRouter {
                    switch {
                        routeList.forEach {
                            route(it.path,it.kClass,it.exact)
                        }

                    }
                }
            }
        }

    }
}