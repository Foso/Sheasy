package de.jensklingenberg.sheasy.ui.about

import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import de.jensklingenberg.sheasy.App
import de.jensklingenberg.sheasy.R
import de.jensklingenberg.sheasy.ui.common.BaseFragment
import de.jensklingenberg.sheasy.utils.extension.obtainViewModel
import kotlinx.android.synthetic.main.fragment_apps.*


class AboutFragment : BaseFragment() {

    private val aboutAdapter = AboutAdapter()
    lateinit var aboutViewModel: AboutViewModel

    init {
        initializeDagger()
    }

    private fun initializeDagger() = App.appComponent.inject(this)

    override fun getLayoutId(): Int = R.layout.fragment_about


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        aboutViewModel = obtainViewModel(AboutViewModel::class.java)

        recyclerView?.apply {
            adapter = aboutAdapter
            recyclerView.layoutManager = LinearLayoutManager(context)
        }

        aboutAdapter.setItems(aboutViewModel.loadApps())


    }


}
