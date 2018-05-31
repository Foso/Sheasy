package de.jensklingenberg.sheasy.ui.apps

import android.arch.lifecycle.Observer
import android.content.ClipData
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.PopupMenu
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import de.jensklingenberg.sheasy.App
import de.jensklingenberg.sheasy.R
import de.jensklingenberg.sheasy.data.viewmodel.ProfileViewModel
import de.jensklingenberg.sheasy.model.AppsResponse
import de.jensklingenberg.sheasy.utils.extension.getClipboardMangaer
import de.jensklingenberg.sheasy.model.Event
import de.jensklingenberg.sheasy.ui.EventLog.EventAdapter
import de.jensklingenberg.sheasy.ui.common.BaseFragment
import de.jensklingenberg.sheasy.ui.common.ITabView
import de.jensklingenberg.sheasy.ui.filemanager.FilesFragment
import kotlinx.android.synthetic.main.component_toolbar.*
import kotlinx.android.synthetic.main.fragment_apps.*
import javax.inject.Inject


/**
 * Created by jens on 1/4/18.
 */
class AppsFragment : BaseFragment(), AppsAdapter.OnTagClickListener, ITabView {

    lateinit var profileViewModel: ProfileViewModel

    val filesAdapter = AppsAdapter()


    override fun onTagClicked(view: View, tag: AppsResponse) {
        profileViewModel.showPopup(context!!, view)

    }

    override fun getTabNameResId() = R.string.main_frag_tab_name

    override fun getLayoutId() = R.layout.fragment_apps

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        profileViewModel = obtainProfileViewModel()

        filesAdapter.onTagClickListener = this
        recyclerView?.apply {
            adapter = filesAdapter
            recyclerView.layoutManager = LinearLayoutManager(context)
        }
        initObserver()
        pageTitleTv.text = "Apps"
        profileViewModel.getApps()


    }

    private fun initObserver() {
        profileViewModel.apps.observe(this, Observer { filesAdapter.setItems(it!!) })
    }


    companion object {
        @JvmStatic
        fun newInstance() = AppsFragment()
    }


}