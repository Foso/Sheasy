package de.jensklingenberg.sheasy.ui.apps

import android.view.View
import de.jensklingenberg.sheasy.App
import de.jensklingenberg.sheasy.data.FileDataSource
import de.jensklingenberg.sheasy.model.AppInfo
import de.jensklingenberg.sheasy.model.Resource
import de.jensklingenberg.sheasy.ui.common.addTo
import de.jensklingenberg.sheasy.ui.common.toSourceItem
import de.jensklingenberg.sheasy.utils.UseCase.ShareUseCase
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.subscribeBy
import io.reactivex.schedulers.Schedulers
import io.reactivex.subjects.PublishSubject
import javax.inject.Inject

class AppsPresenter(val view: AppsContract.View) : AppsContract.Presenter {


    val appsSubject: PublishSubject<Resource<List<AppInfo>>> = PublishSubject.create<Resource<List<AppInfo>>>()

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


    override fun onMoreButtonClicked(view: View, payload: Any) {
        this.view.onMoreButtonClicked(view, payload)
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
                    Resource.success(appsList.filter {
                        it.name.contains(query, ignoreCase = true)
                    })
                )
            }, onError = {
                snackbar.onError(it)
            }).addTo(compositeDisposable)
    }

    fun getApps(): Observable<Resource<List<AppInfo>>> {
        return appsSubject.hide()
    }

    override fun shareApp(appInfo: AppInfo) {
        shareUseCase.share(fileDataSource.getTempFile(appInfo))
    }

    override fun extractApp(appInfo: AppInfo): Boolean {
        return fileDataSource.extractApk(appInfo)
    }

    private fun loadApps() {
        getApps()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeBy(onNext = { appList ->
                appList.data!!
                    .sortedBy { it.name }
                    .map { it.toSourceItem(this) }
                    .run {
                        view.setData(this)
                    }
            }, onError = {
                view.showError(it)

            }).addTo(compositeDisposable)
    }



}