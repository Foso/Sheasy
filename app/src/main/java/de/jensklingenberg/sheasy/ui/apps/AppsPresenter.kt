package de.jensklingenberg.sheasy.ui.apps

import android.view.View
import de.jensklingenberg.sheasy.App
import de.jensklingenberg.sheasy.data.FileDataSource
import de.jensklingenberg.sheasy.model.AppInfo
import de.jensklingenberg.sheasy.model.Resource
import de.jensklingenberg.sheasy.ui.common.OnEntryClickListener
import de.jensklingenberg.sheasy.ui.common.toSourceItem
import de.jensklingenberg.sheasy.utils.UseCase.ShareUseCase
import de.jensklingenberg.sheasy.utils.extension.requireView
import io.reactivex.Observable
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.rxkotlin.subscribeBy
import io.reactivex.schedulers.Schedulers
import io.reactivex.subjects.PublishSubject
import javax.inject.Inject

class AppsPresenter(val view : AppsContract.View) : AppsContract.Presenter, OnEntryClickListener {
    override fun onItemClicked(payload: Any) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun onMoreButtonClicked(view: View, payload: Any) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    val appsSubject: PublishSubject<Resource<List<AppInfo>>> = PublishSubject.create<Resource<List<AppInfo>>>()

    @Inject
    lateinit var fileDataSource: FileDataSource

    private var query: String = ""

    @Inject
    lateinit var shareUseCase: ShareUseCase

    val snackbar: PublishSubject<String> = PublishSubject.create()

    override fun onCreate() {

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
                })



            getSnackbar()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnError {
                    view.showError(it)

                }
                .subscribe()


    }



    override fun onDestroy() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    init {
        initializeDagger()
        loadApps()
    }

    private fun initializeDagger() = App.appComponent.inject(this)

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
                    }))
            }, onError = {
                snackbar.onError(Throwable("EROR"))
            })
    }


    /**
     * @return a stream that emits when a notification should be displayed. The stream contains the
     * notification text
     */
    fun getApps(): Observable<Resource<List<AppInfo>>> {
        return appsSubject.hide()
    }

    fun shareApp(appInfo: AppInfo) {
        shareUseCase.share(fileDataSource.getTempFile(appInfo))
    }

    override fun extractApp(appInfo: AppInfo): Boolean {
        return fileDataSource.extractApk(appInfo)
    }

    fun getSnackbar(): Observable<String> {
        return snackbar.hide()
    }


    private fun loadApps() {
        fileDataSource
            .getApps()
            .subscribeOn(Schedulers.newThread())
            .observeOn(Schedulers.newThread())
            .subscribeBy(onSuccess = {
                appsSubject.onNext(Resource.success(it))
            }, onError = {
                snackbar.onError(Throwable("EROR"))
            })
    }

    fun test(): Single<List<AppInfo>> {
        return fileDataSource
            .getApps()
    }
}