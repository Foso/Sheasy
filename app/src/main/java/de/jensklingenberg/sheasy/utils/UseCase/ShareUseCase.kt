package de.jensklingenberg.sheasy.utils.UseCase

import android.app.Application
import android.content.Intent
import de.jensklingenberg.sheasy.App
import de.jensklingenberg.sheasy.R
import javax.inject.Inject

class ShareUseCase(){

    @Inject
    lateinit var application: Application

    init {
        initializeDagger()
    }

    fun initializeDagger() = App.appComponent.inject(this)




fun share(){
    val shareIntent: Intent = Intent().apply {
        action = Intent.ACTION_SEND
        putExtra(Intent.EXTRA_TEXT, "This is my text to send.")
        type = "text/plain"
    }
    application.startActivity(Intent.createChooser(shareIntent, application.resources.getText(R.string.share)))
}


}