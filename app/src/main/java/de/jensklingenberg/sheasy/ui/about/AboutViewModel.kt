package de.jensklingenberg.sheasy.ui.about

import androidx.lifecycle.ViewModel
import de.jensklingenberg.sheasy.App
import de.jensklingenberg.sheasy.BuildConfig

typealias AboutItem = Pair<String, String>

class AboutViewModel : ViewModel() {


    init {
        initializeDagger()
    }

    private fun initializeDagger() = App.appComponent.inject(this)


    fun loadApps(): List<AboutItem> {
        return listOf(AboutItem("Version", BuildConfig.APP_VERSION))
    }


}
