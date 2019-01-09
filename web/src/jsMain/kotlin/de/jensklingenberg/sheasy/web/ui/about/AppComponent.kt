package de.jensklingenberg.sheasy.web.ui.about

import de.jensklingenberg.sheasy.web.ui.apps.AppsView


interface AppComponent {
    fun inject(aboutView: AboutView)
    fun inject(aboutView: AppsView)


}