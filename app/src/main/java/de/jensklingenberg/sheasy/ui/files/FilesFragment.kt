package de.jensklingenberg.sheasy.ui.files

import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.shopify.livedataktx.nonNull
import com.shopify.livedataktx.observe
import de.jensklingenberg.sheasy.R
import de.jensklingenberg.sheasy.ui.common.BaseAdapter
import de.jensklingenberg.sheasy.ui.common.BaseFragment
import de.jensklingenberg.sheasy.ui.common.OnEntryClickListener
import de.jensklingenberg.sheasy.utils.extension.obtainViewModel
import de.jensklingenberg.sheasy.utils.extension.toSourceitem
import kotlinx.android.synthetic.main.fragment_files.*
import model.FileResponse


class FilesFragment : BaseFragment(), OnEntryClickListener {

    lateinit var filesViewModel: FilesViewModel
    private val baseAdapter = BaseAdapter()

    override fun getLayoutId() = R.layout.fragment_files

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        filesViewModel = obtainViewModel(FilesViewModel::class.java)


        recyclerView?.apply {
            adapter = baseAdapter
            recyclerView.layoutManager = LinearLayoutManager(context)
            addItemDecoration(
                DividerItemDecoration(
                    requireContext(),
                    DividerItemDecoration.VERTICAL
                )
            )
        }
        updateFolderPathInfo(filesViewModel.filePath)

        initObserver()
        filesViewModel.loadFiles()
        folderUpIv.setOnClickListener { filesViewModel.folderUp() }

    }

    private fun initObserver() {
        filesViewModel
            .files
            .nonNull()
            .observe {
                updateFolderPathInfo(filesViewModel.filePath)

                it
                    .sortedBy { it.name }
                    .map { file ->
                        file.toSourceitem(this)
                    }
                    .run {
                        baseAdapter.dataSource.setItems(this)
                        baseAdapter.notifyDataSetChanged()
                    }
            }
    }


    override fun onItemClicked(payload: Any) {
        val item = payload as FileResponse
        filesViewModel.filePath = item.path
        filesViewModel.loadFiles()
        updateFolderPathInfo(item.path)
    }

    fun updateFolderPathInfo(path: String) {
        folderPathLayout?.apply {
            title.text = path
        }
    }


}

