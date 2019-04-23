package de.jensklingenberg.sheasy.ui.screen

import android.content.Intent
import android.os.Bundle
import android.view.View
import de.jensklingenberg.sheasy.App
import de.jensklingenberg.sheasy.R
import de.jensklingenberg.sheasy.ui.common.BaseFragment
import de.jensklingenberg.sheasy.utils.ScreenRecord
import de.jensklingenberg.sheasy.utils.extension.obtainViewModel
import kotlinx.android.synthetic.main.activity_screen.*


class ScreenCaptureFragment : BaseFragment() {

    companion object {

        val TAG = ScreenCaptureFragment::class.java.name
        private val REQUEST_CODE = 100
    }


    lateinit var appsViewModel: ScreenViewModel
    val screenRecord = ScreenRecord()


    init {
        initializeDagger()
    }

    fun initializeDagger() = App.appComponent.inject(this)


    /****************************************** Activity Lifecycle methods  */


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        appsViewModel = obtainViewModel(ScreenViewModel::class.java)
        startButton.setOnClickListener { startProjection() }
        stopButton.setOnClickListener { stopProjection() }


    }

    override fun getLayoutId(): Int {
        return R.layout.activity_screen
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        when (requestCode) {
            REQUEST_CODE -> {
                screenRecord.startRecord(resultCode, data)
            }
        }
    }

    /****************************************** UI Widget Callbacks  */
    private fun startProjection() {
        startActivityForResult(
            screenRecord.createScreenCaptureIntent(),
            REQUEST_CODE
        )
    }

    private fun stopProjection() {
        screenRecord.stopProjection()
    }


}