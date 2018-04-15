package de.jensklingenberg.sheasy.ui

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentPagerAdapter
import android.support.v7.app.AppCompatActivity
import android.widget.Toast
import de.jensklingenberg.sheasy.R
import de.jensklingenberg.sheasy.ui.main.LogFragment
import de.jensklingenberg.sheasy.ui.viewmodel.ProfileViewModel
import de.jensklingenberg.sheasy.ui.viewmodel.ViewModelFactory
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity() {
lateinit var permissionOverViewFragment: PermissionOverViewFragment
    lateinit var mainFragment: MainFragment
    var fragmentPagerAdapter: FragmentPagerAdapter? = null
    lateinit var profileViewModel: ProfileViewModel

    companion object {
        val REQUEST_PERMISSIONS = 100

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        profileViewModel = ViewModelFactory.obtainProfileViewModel(this)

        when (intent.extras) {
             null -> {
                 changeFragment(RootFragment.newInstance(),false)

             }
            else -> {
                intent?.let {
                    val action = it.action
                    val type = it.type

                    when(action){
                        Intent.ACTION_SEND->{
                            handleSendImage(it)

                            Toast.makeText(this,"Hallo", Toast.LENGTH_LONG).show()
                        }

                        Intent.ACTION_SEND_MULTIPLE->{
                            handleSendMultipleImages(it)
                        }
                        else -> {
                        }
                    }
                }
            }
        }






        //initViewPager()
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
            profileViewModel.setSharedFolder(path)
changeFragment(ShareFragment.newInstance(),false)
        }
    }

    fun changeFragment(fragment: Fragment, keepInstance: Boolean) {

            val trans = supportFragmentManager.beginTransaction()
            if (keepInstance) {
                trans.add(R.id.profileContainer, fragment)
            } else {
                trans.replace(R.id.profileContainer, fragment)
            }

            trans.addToBackStack(null)

            trans.commit()


    }



}

