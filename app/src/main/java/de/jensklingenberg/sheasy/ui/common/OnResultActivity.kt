package de.jensklingenberg.sheasy.ui.common

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import de.jensklingenberg.sheasy.App
import de.jensklingenberg.sheasy.network.HTTPServerService
import de.jensklingenberg.sheasy.utils.ScreenRecord
import javax.inject.Inject


/**
 * This Activity is used to by a Service when onActivtyResult is needed
 */
class OnResultActivity : Activity() {

    companion object {
        var REQUEST_CODE = 1234
        var RESULT_CODE = "RESULT_CODE"

    }

    @Inject
    lateinit var screenRecord: ScreenRecord

    init {
        initializeDagger()

    }

    private fun initializeDagger() = App.appComponent.inject(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        startActivityForResult(
            screenRecord.createScreenCaptureIntent(),
            REQUEST_CODE
        )

    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {

        data?.let {
            it.action = HTTPServerService.ACTION_ON_ACTIVITY_RESULT
            it.putExtra(RESULT_CODE, resultCode)
            sendBroadcast(data)
        }
        finish()

    }

}