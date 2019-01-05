package de.jensklingenberg.sheasy.utils.UseCase

import android.app.Application
import android.content.Intent
import android.net.Uri
import de.jensklingenberg.sheasy.App
import java.io.File
import java.net.URLConnection
import javax.inject.Inject


class ShareUseCase {

    @Inject
    lateinit var application: Application

    init {
        initializeDagger()
    }

    private fun initializeDagger() = App.appComponent.inject(this)




fun shareDownloadLink(link:String){
    val shareIntent: Intent = Intent().apply {
        action = Intent.ACTION_SEND
        putExtra(Intent.EXTRA_TEXT, "This is my text to send."+link)
        type = "text/plain"
    }
    application.startActivity(Intent.createChooser(shareIntent, application.resources.getText(de.jensklingenberg.sheasy.R.string.share)))
}


    fun share(file:File){

        val intentShareFile = Intent(Intent.ACTION_SEND)

        intentShareFile.type = URLConnection.guessContentTypeFromName(file.getName())
        intentShareFile.putExtra(
            Intent.EXTRA_STREAM,
            Uri.parse("file://" + file.getAbsolutePath())
        )

        //if you need
        //intentShareFile.putExtra(Intent.EXTRA_SUBJECT,"Sharing File Subject);
        //intentShareFile.putExtra(Intent.EXTRA_TEXT, "Sharing File Description");

        application.startActivity(Intent.createChooser(intentShareFile, "Share File"))
    }

}