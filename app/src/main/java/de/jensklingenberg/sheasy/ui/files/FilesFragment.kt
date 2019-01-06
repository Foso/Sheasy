package de.jensklingenberg.sheasy.ui.files

import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.annotation.NonNull
import androidx.appcompat.widget.PopupMenu
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.shopify.livedataktx.nonNull
import com.shopify.livedataktx.observe
import de.jensklingenberg.sheasy.App
import de.jensklingenberg.sheasy.R
import de.jensklingenberg.sheasy.model.FileResponse
import de.jensklingenberg.sheasy.ui.common.BaseAdapter
import de.jensklingenberg.sheasy.ui.common.BaseFragment
import de.jensklingenberg.sheasy.ui.common.OnEntryClickListener
import de.jensklingenberg.sheasy.utils.PermissionUtils
import de.jensklingenberg.sheasy.utils.UseCase.MessageUseCase
import de.jensklingenberg.sheasy.utils.UseCase.ShareUseCase
import de.jensklingenberg.sheasy.utils.extension.obtainViewModel
import kotlinx.android.synthetic.main.fragment_files.*
import de.jensklingenberg.sheasy.model.checkState
import de.jensklingenberg.sheasy.ui.common.toSourceitem
import java.io.File
import javax.inject.Inject


class FilesFragment : BaseFragment(), OnEntryClickListener {


    @Inject
    lateinit var permissionUtils: PermissionUtils

    @Inject
    lateinit var shareUseCase: ShareUseCase

    private val REQUEST_CAMERA_PERMISSION = 1

    @Inject
    lateinit var messageUseCase: MessageUseCase

    lateinit var filesViewModel: FilesViewModel
    private val baseAdapter = BaseAdapter()

    /****************************************** Lifecycle methods  */
    init {
        initializeDagger()
    }

    fun initializeDagger() = App.appComponent.inject(this)

    override fun getLayoutId() = R.layout.fragment_files

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        // permissionUtils.checkPermStorage(requireActivity(), Manifest.permission.WRITE_EXTERNAL_STORAGE)
        // This is for runtime permission on Marshmallow and above; It is not directly related to
        // PermissionRequest API.
        if (ContextCompat.checkSelfPermission(
                requireActivity(),
                Manifest.permission.READ_EXTERNAL_STORAGE
            ) !== PackageManager.PERMISSION_GRANTED
        ) {
            permissionUtils.requestPermission(this,REQUEST_CAMERA_PERMISSION)
        } else {
            1 == 1
        }

        filesViewModel = obtainViewModel(FilesViewModel::class.java)
        arguments?.let {
            val fromBundle = FilesFragmentArgs.fromBundle(arguments)
            var filepath = fromBundle.filePath
            if (filepath.isNotBlank()) {

                filepath = filepath.replace("file://", "")
                if (filepath.contains(".")) {
                    filepath = filepath.replaceAfterLast("/", "")
                }
                filesViewModel.filePath = filepath

            }

        }
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

    override fun onResume() {
        super.onResume()
        //   permissionUtils.checkPermStorage(requireActivity(), Manifest.permission.WRITE_EXTERNAL_STORAGE)
        // This is for runtime permission on Marshmallow and above; It is not directly related to
        // PermissionRequest API.
        if (ContextCompat.checkSelfPermission(
                requireActivity(),
                Manifest.permission.READ_EXTERNAL_STORAGE
            ) !== PackageManager.PERMISSION_GRANTED
        ) {
            permissionUtils.requestPermission(this,REQUEST_CAMERA_PERMISSION)
        } else {
            1 == 1
        }
    }

    /****************************************** Listener methods  */
    override fun onItemClicked(payload: Any) {
        val item = payload as FileResponse
        filesViewModel.filePath = item.path
        filesViewModel.loadFiles()
        updateFolderPathInfo(item.path)
    }

    override fun onMoreButtonClicked(view: View, payload: Any) {
        val item = payload as? FileResponse
        item?.let {
            val popup = PopupMenu(requireActivity(), view).apply {
                menuInflater.inflate(R.menu.files_actions, this.menu);
                setOnMenuItemClickListener {
                    when (it.itemId) {
                        R.id.menu_share -> {
                            shareUseCase.share(File(item.path))
                        }
                    }
                    true
                }
            }

            popup.show()
        }


    }

    /****************************************** Class methods  */



    override fun onRequestPermissionsResult(
        requestCode: Int, @NonNull permissions: Array<String>,
        @NonNull grantResults: IntArray
    ) {
        // This is for runtime permission on Marshmallow and above; It is not directly related to
        // PermissionRequest API.
        if (requestCode == REQUEST_CAMERA_PERMISSION) {
            if (permissions.size != 1 || grantResults.size != 1 ||
                grantResults[0] != PackageManager.PERMISSION_GRANTED
            ) {
                Log.d("Fragment", "Camera permission not granted.")
            } else {
                Log.d("Fragment", "Camera permission is granted.")
            }
        } else {
            super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        }
    }

    private fun initObserver() {
        filesViewModel
            .files
            .nonNull()
            .observe {
                it.checkState(
                    onSuccess = {resource->
                        resource.data!!.sortedBy { file -> file.name }
                            .map { file ->
                                file.toSourceitem(this)
                            }
                            .run {
                                baseAdapter.dataSource.setItems(this)
                                baseAdapter.notifyDataSetChanged()
                            }

                    },
                    onLoading = {
                        messageUseCase.show(view!!,"Loading")
                    }
                    , onError = {
                        messageUseCase.show(view!!,"Files could not loaded")
                    })


                updateFolderPathInfo(filesViewModel.filePath)


            }
    }

    private fun updateFolderPathInfo(path: String) {
        folderPathLayout?.apply {
            title.text = path
        }
    }


}

