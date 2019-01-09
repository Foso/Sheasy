package de.jensklingenberg.sheasy.web.ui.about

import de.jensklingenberg.sheasy.web.KodeinModul
import de.jensklingenberg.sheasy.web.ui.apps.AppsView
import de.jensklingenberg.sheasy.web.ui.apps.UseCaseModule

@KodeinModul(modules = [UseCaseModule::class])
interface AppComponent {
    fun inject(aboutView: AboutView)
    fun inject(aboutView: AppsView)


}