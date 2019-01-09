package de.jensklingenberg.sheasy.web.di

import de.jensklingenberg.sheasy.web.KodeinModul
import de.jensklingenberg.sheasy.web.ui.about.AboutView
import de.jensklingenberg.sheasy.web.ui.about.AppComponent
import de.jensklingenberg.sheasy.web.ui.apps.UseCaseModule
import de.jensklingenberg.sheasy.web.ui.apps.AppsView

@KodeinModul(modules = [(UseCaseModule::class)])

class DaggerAppComponent: AppComponent {
    override fun inject(aboutView: AppsView) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }


    override fun inject(aboutView: AboutView) {

    }


}
