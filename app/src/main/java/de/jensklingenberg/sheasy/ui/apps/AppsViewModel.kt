package de.jensklingenberg.sheasy.ui.apps

import androidx.lifecycle.ViewModel
import de.jensklingenberg.sheasy.App
import de.jensklingenberg.sheasy.data.FileDataSource
import model.AppFile
import javax.inject.Inject


class AppsViewModel : ViewModel() {

    @Inject
    lateinit var fileDataSource: FileDataSource

    init {
        initializeDagger()
    }

    private fun initializeDagger() = App.appComponent.inject(this)


    fun loadApps(): List<AppFile> {
        return fileDataSource.getApps()
    }


}
