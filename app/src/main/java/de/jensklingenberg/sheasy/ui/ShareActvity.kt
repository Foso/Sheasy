package de.jensklingenberg.sheasy.ui

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.widget.Toast
import de.jensklingenberg.sheasy.R
import de.jensklingenberg.sheasy.data.viewmodel.CommonViewModel
import de.jensklingenberg.sheasy.data.viewmodel.ViewModelFactory

class ShareActvity : AppCompatActivity() {


    lateinit var profileViewModel: CommonViewModel


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_share_actvity)
        profileViewModel = ViewModelFactory.obtainProfileViewModel(this)


        intent?.let {
            val action = it.action
            val type = it.type

            when (action) {
                Intent.ACTION_SEND -> {
                    handleSendImage(it)

                    Toast.makeText(this, "Hallo", Toast.LENGTH_LONG).show()
                }

                Intent.ACTION_SEND_MULTIPLE -> {
                    handleSendMultipleImages(it)
                }
                else -> {
                }
            }
        }


    }

    fun handleSendImage(intent: Intent) {
        val imageUri = intent.getParcelableExtra<Uri>(Intent.EXTRA_STREAM)
        if (imageUri != null) {
            // Update UI to reflect image being shared
        }
    }


    fun handleSendMultipleImages(intent: Intent) {
        val imageUris = intent.getParcelableArrayListExtra<Uri>(Intent.EXTRA_STREAM)
        if (imageUris != null) {
            // Update UI to reflect multiple images being shared
            val path = imageUris.first().path
        }
    }

}
