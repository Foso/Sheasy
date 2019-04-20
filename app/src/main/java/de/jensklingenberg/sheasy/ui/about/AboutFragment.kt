package de.jensklingenberg.sheasy.ui.about

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.View
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.mikepenz.aboutlibraries.Libs
import com.mikepenz.aboutlibraries.LibsBuilder
import de.jensklingenberg.sheasy.App
import de.jensklingenberg.sheasy.R
import de.jensklingenberg.sheasy.ui.common.BaseAdapter
import de.jensklingenberg.sheasy.ui.common.BaseDataSourceItem
import de.jensklingenberg.sheasy.ui.common.BaseFragment
import de.jensklingenberg.sheasy.ui.common.GenericListItemSourceItem
import de.jensklingenberg.sheasy.data.usecase.ShareUseCase
import kotlinx.android.synthetic.main.fragment_apps.*
import javax.inject.Inject


class AboutFragment : BaseFragment(), AboutContract.View {


    private val aboutAdapter = BaseAdapter()

    lateinit var presenter: AboutPresenter

    @Inject
    lateinit var shareUseCase: ShareUseCase

    /****************************************** Fragment Lifecycle methods  */

    init {
        initializeDagger()
    }

    fun initializeDagger() = App.appComponent.inject(this)


    override fun getLayoutId(): Int = R.layout.fragment_about


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupRecyclerView()
        presenter = AboutPresenter(this)
        presenter.onCreate()

    }

    private fun setupRecyclerView() {
        recyclerView?.apply {
            adapter = aboutAdapter
            recyclerView.layoutManager = LinearLayoutManager(context)
        }
    }

    /****************************************** Listener methods  */


    override fun onItemClicked(payload: Any) {
        when (val item = payload) {

            is GenericListItemSourceItem -> {
                val genericListItem = item.getPayload()
                when (genericListItem?.title) {
                    getString(R.string.about_libraries) -> {
                        LibsBuilder()
                            .withActivityStyle(Libs.ActivityStyle.LIGHT_DARK_TOOLBAR)
                            .start(requireActivity())
                    }

                    getString(R.string.About_Changelog) -> {
                        val browserIntent =
                            Intent(
                                Intent.ACTION_VIEW,
                                Uri.parse(getString(R.string.about_changelog_link))
                            )
                        startActivity(browserIntent)
                    }
                    getString(R.string.about_License) -> {
                        val browserIntent =
                            Intent(
                                Intent.ACTION_VIEW,
                                Uri.parse(getString(R.string.about_license_link))
                            )
                        ContextCompat.startActivity(requireContext(), browserIntent, null)
                    }

                    "Feedback" -> {
                        startActivity(shareUseCase.feedbackMailIntent())
                    }
                }

            }
        }

    }


    override fun setData(items: List<BaseDataSourceItem<*>>) {
        aboutAdapter.dataSource.setItems(items)
    }


}
