package de.jensklingenberg.sheasy.ui.files

import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import androidx.annotation.NonNull
import androidx.appcompat.widget.PopupMenu
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.jakewharton.rxbinding3.appcompat.itemClicks
import com.shopify.livedataktx.nonNull
import com.shopify.livedataktx.observe
import de.jensklingenberg.sheasy.App
import de.jensklingenberg.sheasy.R
import de.jensklingenberg.sheasy.model.FileResponse
import de.jensklingenberg.sheasy.ui.common.BaseAdapter
import de.jensklingenberg.sheasy.ui.common.BaseFragment
import de.jensklingenberg.sheasy.utils.PermissionUtils
import de.jensklingenberg.sheasy.utils.UseCase.MessageUseCase
import de.jensklingenberg.sheasy.utils.UseCase.ShareUseCase
import kotlinx.android.synthetic.main.fragment_files.*
import de.jensklingenberg.sheasy.model.checkState
import de.jensklingenberg.sheasy.network.HTTPServerService
import de.jensklingenberg.sheasy.ui.apps.rxBindingSubscriber
import de.jensklingenberg.sheasy.ui.common.toSourceitem
import de.jensklingenberg.sheasy.utils.extension.requireView
import io.reactivex.rxkotlin.subscribeBy
import io.reactivex.schedulers.Schedulers
import java.io.File
import javax.inject.Inject


class FilesFragment : BaseFragment(),FilesContract.View {



    @Inject
    lateinit var permissionUtils: PermissionUtils

    @Inject
    lateinit var shareUseCase: ShareUseCase

    private val REQUEST_CAMERA_PERMISSION = 1

    @Inject
    lateinit var messageUseCase: MessageUseCase

    private val baseAdapter = BaseAdapter()
    lateinit var presenter: FilesPresenter

    var toolbarMenu : Menu?=null

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
        setHasOptionsMenu(true);
        if (ContextCompat.checkSelfPermission(
                requireActivity(),
                Manifest.permission.READ_EXTERNAL_STORAGE
            ) !== PackageManager.PERMISSION_GRANTED
        ) {
            permissionUtils.requestPermission(this,REQUEST_CAMERA_PERMISSION)
        } else {
            1 == 1
        }

        presenter=FilesPresenter(this)

        arguments?.let {
            val fromBundle = FilesFragmentArgs.fromBundle(it)
            var filepath = fromBundle.filePath
            if (filepath.isNotBlank()) {

                filepath = filepath.replace("file://", "")
                if (filepath.contains(".")) {
                    filepath = filepath.replaceAfterLast("/", "")
                }
                presenter.filePath = filepath

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
        updateFolderPathInfo(presenter.filePath)

        initObserver()
        presenter.loadFiles()
        folderUpIv.setOnClickListener { presenter.folderUp() }
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



    override fun showPopup(item: FileResponse?, view: View) {
        item?.let { appInfo->
            val popup = PopupMenu(requireContext(), view)
                .apply {
                    menuInflater
                        .inflate(R.menu.files_actions, menu)
                }
                .also {
                    it.itemClicks()
                        .rxBindingSubscriber()
                        .doOnNext { menuItem ->
                            when (menuItem.itemId) {
                                R.id.menu_share -> {
                                    shareUseCase.share(File(item.path))
                                }
                                R.id.menu_share_to_server -> {
                                    shareUseCase.hostFolder(item)
                                }
                            }
                        }.subscribe()
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
        presenter
            .files
            .nonNull()
            .observe {
                it.checkState(
                    onSuccess = {resource->
                        resource.data!!.sortedBy { file : FileResponse -> file.name }
                            .map { file ->
                                file.toSourceitem(presenter)
                            }
                            .run {
                                baseAdapter.dataSource.setItems(this)
                                baseAdapter.notifyDataSetChanged()
                            }

                    },
                    onLoading = {
                        messageUseCase.show(requireView(),"Loading")
                    }
                    , onError = {
                        messageUseCase.show(requireView(),"Files could not loaded")
                    })


                updateFolderPathInfo(presenter.filePath)


            }
    }

    override fun updateFolderPathInfo(path: String) {
        folderPathLayout?.apply {
            title.text = path
        }
    }


    override fun onCreateOptionsMenu(menu: Menu?, inflater: MenuInflater?) {
        menu?.clear()
        inflater?.inflate(R.menu.fragment_apps_options_menu, menu)
        toolbarMenu = menu
        val server = menu?.findItem(R.id.menu_server)
       subscribe(
           HTTPServerService.appsSubject
               .subscribeOn(Schedulers.newThread())
               .observeOn(Schedulers.newThread())
               .subscribeBy(onNext = {running->
                   if(running){
                       server?.setIcon(R.drawable.ic_router_green_700_24dp)

                   }else{
                       server?.setIcon(R.drawable.ic_router_black_24dp)
                   }

               })
       )


       // findItem?.setIcon(R.drawable.ic_router_green_700_24dp)



    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when (item?.itemId) {
            android.R.id.home -> {
                getBaseActivity().mainActivityDrawer.toggleDrawer()
            }
            R.id.menu_server -> {
                when (item.isChecked) {
                    true -> {
                        requireContext().stopService(HTTPServerService.getIntent(requireContext()))
                        item.isChecked = false
                    }
                    false -> {
                        requireContext().startService(HTTPServerService.getIntent(requireContext()))

                        item.isChecked = true
                        //  item.setIcon(R.drawable.ic_router_green_700_24dp)

                    }
                }
            }
        }

        return true
    }





}

