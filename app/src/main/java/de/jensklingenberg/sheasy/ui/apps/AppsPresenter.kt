package de.jensklingenberg.sheasy.ui.apps

import android.view.View
import androidx.appcompat.widget.PopupMenu
import com.jakewharton.rxbinding3.appcompat.itemClicks
import de.jensklingenberg.sheasy.App
import de.jensklingenberg.sheasy.R
import de.jensklingenberg.sheasy.data.FileDataSource
import de.jensklingenberg.sheasy.data.usecase.ShareUseCase
import de.jensklingenberg.sheasy.model.AndroidAppInfo
import de.jensklingenberg.sheasy.model.AppInfo
import de.jensklingenberg.sheasy.ui.common.addTo
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.subscribeBy
import io.reactivex.schedulers.Schedulers
import io.reactivex.subjects.PublishSubject
import javax.inject.Inject

class AppsPresenter(val view: AppsContract.View) : AppsContract.Presenter {


    private val snackbar: PublishSubject<String> = PublishSubject.create()

    private val appsSubject: PublishSubject<List<AppInfo>> = PublishSubject.create<List<AppInfo>>()

    override val compositeDisposable = CompositeDisposable()

    @Inject
    lateinit var fileDataSource: FileDataSource


    @Inject
    lateinit var shareUseCaseProvider: ShareUseCase


    init {
        initializeDagger()
    }

    private fun initializeDagger() = App.appComponent.inject(this)

    override fun onCreate() {
        loadApps()
    }

    override fun onDestroy() {
        compositeDisposable.dispose()
    }


    /****************************************** Class methods  */


    override fun searchApp(packageName: String) {
        fileDataSource
            .getApps()
            .subscribeOn(Schedulers.newThread())
            .observeOn(Schedulers.newThread())
            .subscribeBy(onSuccess = { appsList ->
                appsSubject.onNext(
                    appsList.filter {
                        it.name.contains(packageName, ignoreCase = true)
                    }
                )
            }, onError = {
                snackbar.onError(it)
            }).addTo(compositeDisposable)
    }

    fun getApps(): Observable<List<AppInfo>> {
        return appsSubject.hide()
    }

    private fun loadApps() {
        view.showMessage(R.string.state_loading)
        getApps()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .map { appList ->
                appList
                    .sortedBy { it.name }
                    .map {

                        AppInfoSourceItem(it as AndroidAppInfo, this)

                    }
            }
            .subscribeBy(onNext = view::setData, onError = view::showError)
            .addTo(compositeDisposable)
    }

    private fun onPopupMenuClicked(appInfo: AppInfo, id: Int) {
        when (id) {
            R.id.menu_share -> {
                shareUseCaseProvider.shareApp(appInfo)
            }
            R.id.menu_extract -> {
                fileDataSource.extractApk(appInfo)
                    .subscribeBy(
                        onComplete = {
                            view.showMessage(R.string.Success)
                        }
                    ).addTo(compositeDisposable)


            }
            R.id.menu_share_link -> {
                shareUseCaseProvider.shareDownloadLink(appInfo)
            }
        }
    }

    override fun onAppInfoMoreButtonClicked(
        it: View,
        appInfo: AndroidAppInfo
    ) {
        PopupMenu(it.context, it)
            .apply {
                menuInflater
                    .inflate(R.menu.apps_actions, menu)
            }
            .also {
                it.itemClicks()
                    .subscribeOn(AndroidSchedulers.mainThread())
                    .observeOn(AndroidSchedulers.mainThread())
                    .doOnNext { menuItem ->
                        onPopupMenuClicked(appInfo, menuItem.itemId)
                    }.subscribe()
            }.show()

    }


}