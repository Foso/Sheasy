package de.jensklingenberg.sheasy.ui.filemanager

import android.arch.lifecycle.Observer
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.view.View
import de.jensklingenberg.sheasy.R
import de.jensklingenberg.sheasy.data.viewmodel.CommonViewModel
import de.jensklingenberg.sheasy.model.FileResponse
import de.jensklingenberg.sheasy.ui.MainActivity
import de.jensklingenberg.sheasy.ui.common.BaseFragment
import de.jensklingenberg.sheasy.ui.common.ITabView
import kotlinx.android.synthetic.main.component_toolbar_standard.*
import kotlinx.android.synthetic.main.fragment_files.*


/**
 * Created by jens on 1/4/18.
 */
class FilesFragment : BaseFragment(), FilesAdapter.OnEntryClickListener, ITabView {
    override fun onItemClicked(view: View, tag: FileResponse) {
        commonViewModel.showPopup(context, view)
        //commonViewModel.setSharedFolder2("/storage/emulated/0/")

    }


    lateinit var commonViewModel: CommonViewModel

    val filesAdapter = FilesAdapter()


    override fun onTagClicked(filePath: String) {
        commonViewModel.getFiles(filePath)


    }


    override fun getTabNameResId() = R.string.main_frag_tab_name


    override fun getLayoutId() = R.layout.fragment_files

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        commonViewModel = obtainProfileViewModel()

        menuBtn?.apply {
            visibility = View.VISIBLE
            setOnClickListener { (activity as MainActivity).showMenu() }
        }

        upBtn?.apply {
            setOnClickListener { commonViewModel.goFolderUp() }
        }

        filesAdapter.onEntryClickListener = this

        recyclerView?.apply {
            adapter = filesAdapter
            layoutManager = LinearLayoutManager(activity)
        }

        initObserver()

        commonViewModel.getFiles()


    }

    private fun initObserver() {
        commonViewModel.files.observe(this, Observer { filesAdapter.setItems(it!!) })

        commonViewModel.folderPath.observe(this, Observer { pageTitleTv.text = it })
    }


    companion object {
        @JvmStatic
        fun newInstance() = FilesFragment()
    }


}