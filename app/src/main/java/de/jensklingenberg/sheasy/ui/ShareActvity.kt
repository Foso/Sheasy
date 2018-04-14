package de.jensklingenberg.sheasy.ui

import android.content.Intent
import android.net.Uri
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.os.Parcelable
import android.widget.Toast
import de.jensklingenberg.sheasy.R

class ShareActvity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_share_actvity)


        intent?.let {
        val action = it.action
            val type = it.type

            when(action){
                Intent.ACTION_SEND->{
                    handleSendImage(it)

                    Toast.makeText(this,"Hallo",Toast.LENGTH_LONG).show()
                }

                Intent.ACTION_SEND_MULTIPLE->{
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
        }
    }

}
