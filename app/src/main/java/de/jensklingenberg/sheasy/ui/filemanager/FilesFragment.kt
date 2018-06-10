package de.jensklingenberg.sheasy.ui.filemanager

import android.arch.lifecycle.Observer
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import de.jensklingenberg.sheasy.R
import de.jensklingenberg.sheasy.data.viewmodel.CommonViewModel
import de.jensklingenberg.sheasy.ui.MainActivity
import de.jensklingenberg.sheasy.ui.common.BaseFragment
import de.jensklingenberg.sheasy.ui.common.ITabView
import kotlinx.android.synthetic.main.component_toolbar_standard.*
import kotlinx.android.synthetic.main.fragment_files.*


/**
 * Created by jens on 1/4/18.
 */
class FilesFragment : BaseFragment(), FilesAdapter.OnEntryClickListener, ITabView {
    override fun onTagClicked(filePath: String) {
        profileViewModel.getFiles(filePath)


    }


    override fun getTabNameResId() = R.string.main_frag_tab_name


    lateinit var profileViewModel: CommonViewModel

    val filesAdapter = FilesAdapter()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        return inflater.inflate(R.layout.fragment_files, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        menuBtn?.apply {
            visibility = View.VISIBLE
            setOnClickListener { (activity as MainActivity).showMenu() }
        }
        profileViewModel = obtainProfileViewModel()
        filesAdapter.onEntryClickListener = this
        recyclerView.adapter = filesAdapter
        recyclerView.layoutManager = LinearLayoutManager(activity)
        initObserver()
        val folderPath = "/storage/emulated/0/"
        pageTitleTv.text = folderPath
        profileViewModel.getFiles(folderPath)


    }

    private fun initObserver() {
        profileViewModel.files.observe(this, Observer { filesAdapter.setItems(it!!) })
    }


    companion object {
        @JvmStatic
        fun newInstance() = FilesFragment()
    }


}