package de.jensklingenberg.sheasy.ui

import android.content.ClipboardManager
import android.content.Context
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v4.content.ContextCompat
import android.util.Log
import android.provider.Settings
import de.jensklingenberg.sheasy.model.Event
import android.content.ClipData
import android.content.pm.PackageManager
import android.support.v4.app.FragmentPagerAdapter
import de.jensklingenberg.sheasy.R
import de.jensklingenberg.sheasy.ui.PermissionOverViewFragment
import de.jensklingenberg.sheasy.ui.viewmodel.ProfileViewModel
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity(), EventAdapter.OnTagClickListener {
lateinit var permissionOverViewFragment: PermissionOverViewFragment
    lateinit var mainFragment: MainFragment
    var fragmentPagerAdapter: FragmentPagerAdapter? = null
    override fun onTagClicked(tag: Event) {
        val clipboard = getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager

        val clip = ClipData.newPlainText("simple text", tag.text)
        clipboard.primaryClip = clip
    }

    companion object {
        val REQUEST_PERMISSIONS = 100

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initViewPager()
    }

      fun initViewPager() {
          mainFragment = MainFragment.newInstance();
          permissionOverViewFragment=PermissionOverViewFragment.newInstance()
          fragmentPagerAdapter =  OverviewPagerAdapter(supportFragmentManager, listOf(mainFragment,permissionOverViewFragment));
          viewpager.adapter = fragmentPagerAdapter;
}




    override fun onRequestPermissionsResult(requestCode: Int,
                                            permissions: Array<String>,
                                            grantResults: IntArray) {
        when (requestCode) {
            REQUEST_PERMISSIONS -> {
                if (grantResults.isNotEmpty() && grantResults[0] + grantResults[1] == PackageManager.PERMISSION_GRANTED) {
                    //onToggleScreenShare(mToggleButton)
                    Log.d("THIS","ja")

                } else {
                    Log.d("THIS","ney")

                }
                return
            }
        }
    }





}

