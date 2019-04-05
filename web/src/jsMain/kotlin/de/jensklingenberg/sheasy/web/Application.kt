import de.jensklingenberg.sheasy.web.components.router.hashRouter
import de.jensklingenberg.sheasy.web.components.router.route
import de.jensklingenberg.sheasy.web.components.router.switch
import de.jensklingenberg.sheasy.web.data.FileDataSource
import de.jensklingenberg.sheasy.web.data.NetworkPreferences
import de.jensklingenberg.sheasy.web.data.repository.FileRepository
import de.jensklingenberg.sheasy.web.model.Route
import de.jensklingenberg.sheasy.web.network.ReactHttpClient
import de.jensklingenberg.sheasy.web.ui.about.AboutView
import de.jensklingenberg.sheasy.web.ui.apps.AppsView
import de.jensklingenberg.sheasy.web.ui.common.toolbar
import de.jensklingenberg.sheasy.web.ui.files.FileView
import de.jensklingenberg.sheasy.web.ui.home.HomeView
import de.jensklingenberg.sheasy.web.ui.screenshare.ScreenShareView
import de.jensklingenberg.sheasy.web.ui.share.ShareView
import de.jensklingenberg.sheasy.web.usecase.NotificationUseCase
import kotlinext.js.requireAll
import org.kodein.di.Kodein
import org.kodein.di.KodeinAware
import org.kodein.di.erased.bind
import org.kodein.di.erased.singleton
import react.dom.render
import kotlin.browser.document
import kotlin.browser.window


class Application : KodeinAware {


    companion object {
        lateinit var kode: Kodein
    }

    val routeList = listOf(
        Route("/", HomeView::class, true),
        Route("/apps", AppsView::class, exact = true),
        Route("/files", FileView::class, exact = true),
        Route("/about", AboutView::class, exact = true),
        Route("/screenshare", ScreenShareView::class, exact = true),
        Route("/share", ShareView::class, exact = true)


    )

    override val kodein = Kodein {
        bind<FileDataSource>() with singleton { FileRepository(ReactHttpClient(NetworkPreferences())) }
        bind<NotificationUseCase>() with singleton { NotificationUseCase() }
    }

    init {
        kode = kodein
        window.onload = {
            kotlinext.js.require("bootstrap/dist/css/bootstrap.min.css")
            requireAll(kotlinext.js.require.context("kotlin", true, js("/\\.css$/")))

            render(document.getElementById("root")) {

                toolbar()
                hashRouter {
                    switch {
                        routeList.forEach {
                            route(it.path, it.kClass, it.exact)
                        }

                    }
                }
            }
        }

    }
}