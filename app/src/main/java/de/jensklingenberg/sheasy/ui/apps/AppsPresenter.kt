package de.jensklingenberg.sheasy.ui.apps

import de.jensklingenberg.sheasy.App
import de.jensklingenberg.sheasy.R
import de.jensklingenberg.sheasy.data.FileDataSource
import de.jensklingenberg.sheasy.model.AppInfo
import de.jensklingenberg.sheasy.ui.common.addTo
import de.jensklingenberg.sheasy.data.usecase.ShareUseCase
import de.jensklingenberg.sheasy.model.AndroidAppInfo
import io.reactivex.Completable
import io.reactivex.Observable
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.subscribeBy
import io.reactivex.schedulers.Schedulers
import io.reactivex.subjects.PublishSubject
import javax.inject.Inject

class AppsPresenter(val view: AppsContract.View) : AppsContract.Presenter {


    private val appsSubject: PublishSubject<List<AppInfo>> = PublishSubject.create<List<AppInfo>>()

    override val compositeDisposable = CompositeDisposable()

    @Inject
    lateinit var fileDataSource: FileDataSource


    @Inject
    lateinit var shareUseCase: ShareUseCase

    val snackbar: PublishSubject<String> = PublishSubject.create()


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


    override fun searchApp(query: String) {
        fileDataSource
            .getApps()
            .subscribeOn(Schedulers.newThread())
            .observeOn(Schedulers.newThread())
            .subscribeBy(onSuccess = { appsList ->
                appsSubject.onNext(
                    appsList.filter {
                        it.name.contains(query, ignoreCase = true)
                    }
                )
            }, onError = {
                snackbar.onError(it)
            }).addTo(compositeDisposable)
    }

    fun getApps(): Observable<List<AppInfo>> {
        return appsSubject.hide()
    }

    override fun shareApp(appInfo: AppInfo) {
        shareUseCase.shareApp(appInfo)
    }

    override fun extractApp(appInfo: AppInfo): Completable {
        return fileDataSource.extractApk(appInfo)
    }

    private fun loadApps() {
        getApps()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .map { appList ->
                appList
                    .sortedBy { it.name }
                    .map {

                        AppInfoSourceItem(it as AndroidAppInfo,this)

                    }
            }
            .subscribeBy(onNext = view::setData, onError = view::showError)
            .addTo(compositeDisposable)
    }

    override fun onPopupMenuClicked(appInfo: AppInfo, id: Int) {
        when (id) {
            R.id.menu_share -> {
                shareApp(appInfo)
            }
            R.id.menu_extract -> {
                extractApp(appInfo).subscribeBy(
                    onComplete = {
                        view.showMessage(R.string.Success)
                    }
                ).addTo(compositeDisposable)


            }
        }
    }


}