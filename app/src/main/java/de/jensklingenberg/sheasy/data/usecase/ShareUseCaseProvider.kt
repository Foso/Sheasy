package de.jensklingenberg.sheasy.data.usecase

import android.app.Application
import android.content.Context
import android.content.Intent
import android.net.Uri
import androidx.core.content.ContextCompat
import de.jensklingenberg.sheasy.App
import de.jensklingenberg.sheasy.data.FileDataSource
import de.jensklingenberg.sheasy.model.AppInfo
import de.jensklingenberg.sheasy.model.FileResponse
import de.jensklingenberg.sheasy.network.SheasyPrefDataSource
import network.SharedNetworkSettings
import java.io.File
import java.net.URLConnection
import javax.inject.Inject


class ShareUseCaseProvider : ShareUseCase {

    @Inject
    lateinit var application: Application

    @Inject
    lateinit var context: Context


    @Inject
    lateinit var sheasyPrefDataSource: SheasyPrefDataSource

    @Inject
    lateinit var fileDataSource: FileDataSource

    @Inject
    lateinit var getIpUseCase: GetIpUseCase

    init {
        initializeDagger()
    }

    private fun initializeDagger() = App.appComponent.inject(this)


    override fun feedbackMailIntent(): Intent {

        val email = Intent(Intent.ACTION_SEND).apply {
            putExtra(Intent.EXTRA_EMAIL, arrayOf("mail@jensklingenberg.de"))
            putExtra(Intent.EXTRA_SUBJECT, "Feedback: Sheasy")
            type = "message/rfc822"
        }

        return Intent.createChooser(email, "Choose an Email client :")
    }


    override fun share(file: File) {

        val intentShareFile = Intent(Intent.ACTION_SEND).addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)

        intentShareFile.type = URLConnection.guessContentTypeFromName(file.name)
        intentShareFile.putExtra(
            Intent.EXTRA_STREAM,
            Uri.parse("content://" + file.absolutePath)
        )

        //if you need
        //intentShareFile.putExtra(Intent.EXTRA_SUBJECT,"Sharing File Subject);
        //intentShareFile.putExtra(Intent.EXTRA_TEXT, "Sharing File Description");

        application.startActivity(

            Intent.createChooser(intentShareFile, "Share File").addFlags(Intent.FLAG_ACTIVITY_NEW_TASK),
            null
        )
    }

    override fun hostFolder(fileResponse: FileResponse) {
        sheasyPrefDataSource.addShareFolder(fileResponse)

    }

    override fun removeHostFolder(fileResponse: FileResponse) {
        sheasyPrefDataSource.removeShareFolder(fileResponse)
    }

    override fun shareApp(appInfo: AppInfo) {
        share(fileDataSource.createTempFile(appInfo))
    }

    override fun shareDownloadLink(appInfo: AppInfo) {
        shareDownloadLink(SharedNetworkSettings(sheasyPrefDataSource.getBaseUrl()).appDownloadUrl(appInfo.packageName))
    }

    override fun shareDownloadLink(link: FileResponse) {
        shareDownloadLink(SharedNetworkSettings(sheasyPrefDataSource.getBaseUrl()).fileDownloadUrl(link.path))
    }

    override fun shareDownloadLink(message: String) {
        val shareIntent: Intent = Intent().apply {
            action = Intent.ACTION_SEND
            putExtra(Intent.EXTRA_TEXT, message)
            type = "text/plain"
        }
        ContextCompat.startActivity(
            context,
            Intent.createChooser(
                shareIntent,
                application.resources.getText(de.jensklingenberg.sheasy.R.string.share)
            ).addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            , null
        )
    }


}