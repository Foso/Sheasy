package de.jensklingenberg.sheasy.ui.about

import android.content.Context
import android.view.View
import de.jensklingenberg.sheasy.App
import de.jensklingenberg.sheasy.BuildConfig
import de.jensklingenberg.sheasy.R
import de.jensklingenberg.sheasy.ui.common.*
import io.reactivex.disposables.CompositeDisposable
import javax.inject.Inject

class AboutPresenter(val view: AboutContract.View) : AboutContract.Presenter {
    override val compositeDisposable = CompositeDisposable()


    @Inject
    lateinit var context: Context

    init {
        initializeDagger()
    }

    private fun initializeDagger() = App.appComponent.inject(this)


    override fun onCreate() {

        val list = listOf<BaseDataSourceItem<*>>(

            GenericListHeaderSourceItem(
                "App"
            ),
            GenericListItem(
                context.getString(R.string.app_name),
                "v" + BuildConfig.VERSION_NAME,
                R.drawable.ic_info_outline_grey_700_24dp
            ).toSourceItem()

            ,
            GenericListItem(
                context.getString(R.string.About_Changelog),
                "See the latest development changelog",
                R.drawable.ic_history_grey_700_24dp
            ).toSourceItem(this)

            ,
            GenericListItemSourceItem(
                GenericListItem(
                    context.getString(R.string.about_libraries),
                    "List of used open source libraries",
                    R.drawable.ic_code_grey_700_24dp
                ), this
            )
            ,
            GenericListItemSourceItem(
                GenericListItem(
                    context.getString(R.string.privacy_policy),
                    "See the privacy policy of Sheasy",
                    NO_IMAGE
                ), this
            )
            ,
            GenericListItem(
                context.getString(R.string.about_License),
                context.getString(R.string.about_license_caption),
                R.drawable.ic_code_grey_700_24dp
            ).toSourceItem(this),

            GenericListItem(
                "Commit",
                BuildConfig.GIT_SHA,
                NO_IMAGE
            ).toSourceItem(this),
            GenericListHeaderSourceItem(
                "Contact"
            ),
            GenericListItem(
                "Feedback",
                context.getString(R.string.about_send_feedback),
                R.drawable.ic_send_grey_700_24dp
            ).toSourceItem(this),
            GenericListItem(
                "Feature Request",
                context.getString(R.string.about_feature_request_message),
                R.drawable.ic_send_grey_700_24dp
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