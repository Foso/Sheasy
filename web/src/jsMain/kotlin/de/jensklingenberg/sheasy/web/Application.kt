import kotlinext.js.requireAll
import org.kodein.di.Kodein
import org.kodein.di.KodeinAware
import react.dom.render
import de.jensklingenberg.sheasy.web.components.router.hashRouter
import de.jensklingenberg.sheasy.web.components.router.route
import de.jensklingenberg.sheasy.web.components.router.switch
import de.jensklingenberg.sheasy.web.model.Route
import de.jensklingenberg.sheasy.web.ui.home.App
import de.jensklingenberg.sheasy.web.ui.about.AboutView
import de.jensklingenberg.sheasy.web.ui.apps.AppsView
import de.jensklingenberg.sheasy.web.ui.files.FileView
import de.jensklingenberg.sheasy.web.ui.screenshare.ScreenShareView
import kotlin.browser.document
import kotlin.browser.window


class Application : KodeinAware {
    val routeList = listOf(
        Route("/", App::class, true),
        Route("/apps", AppsView::class, exact = true),
        Route("/files", FileView::class, exact = true),
        Route("/about", AboutView::class, exact = true),
        Route("/screenshare", ScreenShareView::class, exact = true)

    )

    override val kodein = Kodein {

    }

    init {
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