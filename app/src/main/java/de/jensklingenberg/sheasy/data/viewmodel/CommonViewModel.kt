package de.jensklingenberg.sheasy.data.viewmodel

import android.app.Activity
import android.app.Application
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import android.content.*
import android.os.IBinder
import android.support.v7.widget.PopupMenu
import android.view.MenuItem
import android.view.View
import de.jensklingenberg.sheasy.App
import de.jensklingenberg.sheasy.R
import de.jensklingenberg.sheasy.broReceiver.MySharedMessageBroadcastReceiver
import de.jensklingenberg.sheasy.interfaces.ApiEventListener
import de.jensklingenberg.sheasy.model.Event
import de.jensklingenberg.sheasy.model.FileResponse
import de.jensklingenberg.sheasy.model.SingleLiveEvent
import de.jensklingenberg.sheasy.network.service.HTTPServerService
import de.jensklingenberg.sheasy.utils.FUtils
import de.jensklingenberg.sheasy.utils.ShareUtils
import javax.inject.Inject


class CommonViewModel @Inject constructor() : ViewModel(),
    ApiEventListener, ServiceConnection {


    lateinit var service: IBinder

    override fun onServiceDisconnected(name: ComponentName?) {


    }

    override fun onServiceConnected(name: ComponentName?, service: IBinder?) {
        service?.let {
            this.service = service
        }
    }


    var shareMessage: MutableLiveData<ArrayList<Event>> = MutableLiveData()
    var shareEvent: SingleLiveEvent<Boolean> = SingleLiveEvent()
    var clickedMenuItem: SingleLiveEvent<MenuItem> = SingleLiveEvent()
    var sharedFolder: MutableLiveData<String> = MutableLiveData()
    var folderPath: MutableLiveData<String> = MutableLiveData()
    val defaultPath = "/storage/emulated/0/"


    var files: MutableLiveData<List<FileResponse>> = MutableLiveData()


    init {
        initializeDagger()
        folderPath.value = defaultPath
    }

    private fun initializeDagger() = App.appComponent.inject(this)


    @Inject
    lateinit var application: Application

    fun getFiles(folderPath: String = defaultPath) {
        this.folderPath.value = defaultPath
        files.value = FUtils.getFilesReponseList(folderPath)
    }

    fun showPopup(context: Context?, v: View) {
        context?.let {
            PopupMenu(context, v).apply {
                inflate(R.menu.files_actions)
                show()
                setOnMenuItemClickListener {
                    clickedMenuItem.value = it
                    true
                }
            }
        }

    }

    fun setSharedFolder2(string: String) {
        HTTPServerService.bind.httpServerService.setShared(string)
    }

    fun share(activity: Activity, selected: String) {
        ShareUtils.appDownload(activity, selected)
        ShareUtils.share(
            activity, ShareUtils.appDownload(activity, selected)
        )

    }


    override fun onShare(test: Event) {
        events.add(test)
        shareMessage.value = events
    }


    fun setSharedFolder(string: String) {
        sharedFolder.value = string
    }

    fun startService(intent: Intent) {
        val filter = IntentFilter(MySharedMessageBroadcastReceiver.ACTION_SHARE)
        val mySharedMessageBroadcastReceiver = App.instance.mySharedMessageBroadcastReceiver
        mySharedMessageBroadcastReceiver.apiEventListener = this

        application.registerReceiver(mySharedMessageBroadcastReceiver, filter)
        application.bindService(intent, this, 0)


    }


    fun stopService(intent: Intent) {
        val filter = IntentFilter(MySharedMessageBroadcastReceiver.ACTION_SHARE)
        val tt = App.instance.mySharedMessageBroadcastReceiver
        tt.apiEventListener = this


        application.registerReceiver(tt, filter)
        application.stopService(intent)

    }

    fun goFolderUp() {
        val oldpath = folderPath.value
        val split = oldpath?.split("/").orEmpty()
        val new = split.joinToString("/")
        folderPath.value = new
        getFiles(new)
    }


    companion object {
        var events = arrayListOf<Event>()
    }


}
