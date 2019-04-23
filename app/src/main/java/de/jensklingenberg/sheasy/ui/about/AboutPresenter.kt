package de.jensklingenberg.sheasy.ui.about

import android.content.Context
import android.view.View
import de.jensklingenberg.sheasy.App
import de.jensklingenberg.sheasy.BuildConfig
import de.jensklingenberg.sheasy.R
import de.jensklingenberg.sheasy.ui.common.*
import de.jensklingenberg.sheasy.data.usecase.ShareUseCase
import io.reactivex.disposables.CompositeDisposable
import javax.inject.Inject

class AboutPresenter(val view: AboutContract.View) : AboutContract.Presenter {
    override val compositeDisposable = CompositeDisposable()


    @Inject
    lateinit var shareUseCase: ShareUseCase

    @Inject
    lateinit var context: Context

    init {
        initializeDagger()
    }

    private fun initializeDagger() = App.appComponent.inject(this)


    override fun onCreate() {

        val list = listOf<BaseDataSourceItem<*>>(
            GenericListHeaderSourceItem(
                "About"
            ),


            GenericListItem(
                context.getString(R.string.app_name),
                "v" + BuildConfig.VERSION_NAME,
                R.drawable.ic_info_outline_grey_700_24dp
            ).toSourceItem()

            ,
            GenericListItem(
                context.getString(R.string.About_Changelog),
                "",
                R.drawable.ic_history_grey_700_24dp
            ).toSourceItem(this)

            ,
            GenericListItemSourceItem(
                GenericListItem(
                    context.getString(R.string.about_libraries),
                    "",
                    R.drawable.ic_code_grey_700_24dp
                ), this
            )
            ,
            GenericListHeaderSourceItem(
                "License"
            ),
            GenericListItem(
                context.getString(R.string.about_License),
                context.getString(R.string.about_license_caption),
                R.drawable.ic_code_grey_700_24dp
            ).toSourceItem(this),
            GenericListItem(
                "Feedback",
                context.getString(R.string.about_send_feedback),
                R.drawable.ic_code_grey_700_24dp
            ).toSourceItem(this),
            GenericListItem(
                "Commit",
                BuildConfig.GIT_SHA,
                R.drawable.ic_code_grey_700_24dp
            ).toSourceItem(this),
            GenericListItem(
                "BuildNumber",
                BuildConfig.VERSION_CODE.toString(),
                R.drawable.ic_code_grey_700_24dp
            ).toSourceItem(this)


        )

        view.setData(list)
    }

    override fun onMoreButtonClicked(view: View, payload: Any) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun onItemClicked(payload: Any) {
        view.onItemClicked(payload)
    }

    override fun onDestroy() {
        compositeDisposable.dispose()
    }

}