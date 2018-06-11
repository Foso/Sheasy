package de.jensklingenberg.sheasy.ui.apps

import android.arch.lifecycle.Observer
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.view.View
import android.widget.Toast
import androidx.core.widget.toast
import de.jensklingenberg.sheasy.R
import de.jensklingenberg.sheasy.data.viewmodel.AppsViewModel
import de.jensklingenberg.sheasy.data.viewmodel.NetworkViewModel
import de.jensklingenberg.sheasy.data.viewmodel.CommonViewModel
import de.jensklingenberg.sheasy.model.AppsResponse
import de.jensklingenberg.sheasy.ui.common.BaseFragment
import de.jensklingenberg.sheasy.ui.common.ITabView
import kotlinx.android.synthetic.main.component_toolbar.*
import kotlinx.android.synthetic.main.fragment_apps.*


/**
 * Created by jens on 1/4/18.
 */
class AppsFragment : BaseFragment(), AppsAdapter.OnTagClickListener, ITabView {

    lateinit var commonViewModel: CommonViewModel
    lateinit var networkViewModel: NetworkViewModel
    lateinit var appsViewModel: AppsViewModel
    val appsAdapter = AppsAdapter()
    var apkPackageName = ""

    override fun onTagClicked(view: View, tag: AppsResponse) {
        apkPackageName = tag.packageName
        appsViewModel.showPopup(context!!, view)

    }

    override fun getTabNameResId() = R.string.apps_title

    override fun getLayoutId() = R.layout.fragment_apps

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        commonViewModel = obtainProfileViewModel()
        appsViewModel = obtainAppsViewModel()

        appsAdapter.onTagClickListener = this
        recyclerView?.apply {
            adapter = appsAdapter
            recyclerView.layoutManager = LinearLayoutManager(context)
        }
        initObserver()
        pageTitleTv.text = getString(R.string.apps_title)
        appsViewModel.getApps()


    }

    private fun initObserver() {

        appsViewModel.toastMessage.observe(
            this,
            Observer { context?.toast(getString(it!!), Toast.LENGTH_SHORT) })

        appsViewModel.apps.observe(this, Observer { appsAdapter.setItems(it!!) })
        appsViewModel.clickedMenuItem.observe(this, Observer {
            when (it?.itemId) {
                R.id.menu_share -> {
                    commonViewModel.share(activity!!, apkPackageName)

                }

                R.id.menu_extract -> {
                    appsViewModel.extractApk(context!!, apkPackageName)

                }

                else -> {
                }
            }
        })

    }


    companion object {
        @JvmStatic
        fun newInstance() = AppsFragment()
    }


}