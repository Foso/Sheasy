package de.jensklingenberg.sheasy.ui.files

import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import de.jensklingenberg.sheasy.App
import de.jensklingenberg.sheasy.R
import de.jensklingenberg.sheasy.data.usecase.MessageUseCase
import de.jensklingenberg.sheasy.model.FileResponse
import de.jensklingenberg.sheasy.ui.common.BaseAdapter
import de.jensklingenberg.sheasy.ui.common.BaseDataSourceItem
import de.jensklingenberg.sheasy.ui.common.BaseFragment
import de.jensklingenberg.sheasy.ui.common.NoOrEmptyContentItem
import de.jensklingenberg.sheasy.utils.extension.requireView
import kotlinx.android.synthetic.main.fragment_files.*
import java.io.File
import javax.inject.Inject


class FilesFragment : BaseFragment(), FilesContract.View {

    @Inject
    lateinit var messageUseCase: MessageUseCase

    private val baseAdapter = BaseAdapter()
    lateinit var presenter: FilesContract.Presenter

    var toolbarMenu: Menu? = null

    /****************************************** Lifecycle methods  */
    init {
        initializeDagger()
    }

    fun initializeDagger() = App.appComponent.inject(this)

    override fun getLayoutId() = R.layout.fragment_files

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setHasOptionsMenu(true)

        presenter = FilesPresenter(this)

        parseArguments()
        setupRecyclerView()
        updateFolderPathInfo(presenter.fileResponse1)
        presenter.onCreate()
        folderUpIv.setOnClickListener { presenter.folderUp() }
    }


    private fun setupRecyclerView() {
        recyclerView?.apply {
            adapter = baseAdapter.apply {
                dataSource.emptyView =
                    NoOrEmptyContentItem("No Files", R.drawable.ic_insert_drive_file_grey_700_24dp).toSourceItem()
            }
            recyclerView.layoutManager = LinearLayoutManager(context)
            addItemDecoration(
                DividerItemDecoration(
                    requireContext(),
                    DividerItemDecoration.VERTICAL
                )
            )
        }
    }

    private fun parseArguments() {
        arguments?.let {
            val fromBundle = FilesFragmentArgs.fromBundle(it)
            var filepath = fromBundle.filePath
            if (filepath.isNotBlank()) {

                filepath = filepath.replace("file://", "")
                if (filepath.contains(".")) {
                    filepath = filepath.replaceAfterLast("/", "")
                }
                presenter.fileResponse1 = FileResponse(File(filepath).name, filepath)

            }

        }
    }


    /****************************************** Listener methods  */


    override fun setData(list: List<BaseDataSourceItem<*>>) {
        baseAdapter.setItems(list)
    }


    /****************************************** Class methods  */

    override fun updateFolderPathInfo(fileResponse: FileResponse) {
        folderPathLayout?.apply {
            folderPath.text = fileResponse.path
        }
        folderName.text = fileResponse.path.substringAfterLast("/")
    }


    override fun onCreateOptionsMenu(menu: Menu?, inflater: MenuInflater?) {
        menu?.clear()
        inflater?.inflate(R.menu.fragment_files_options_menu, menu)
        toolbarMenu = menu
        initSearchView(menu)
    }

    private fun initSearchView(menu: Menu?) {


    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {

        when (item?.itemId) {
            android.R.id.home -> {
                getBaseActivity().mainActivityDrawer.toggleDrawer()
            }
            R.id.menu_share_to_server -> {
                presenter.hostActiveFolder()
            }
            R.id.menu_remove_all_hosted -> {
                presenter.removeAllHostedFolders()
            }
        }

        return true
    }


    override fun showError(it: Throwable) {
        messageUseCase.show(requireView(), it.message.toString())
    }

    override fun onDestroy() {
        presenter.onDestroy()
        super.onDestroy()
    }

}


