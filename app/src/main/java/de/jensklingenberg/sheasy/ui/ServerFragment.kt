package de.jensklingenberg.sheasy.ui

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import de.jensklingenberg.sheasy.App
import de.jensklingenberg.sheasy.R
import de.jensklingenberg.sheasy.data.MyPermission
import de.jensklingenberg.sheasy.data.SheasyPreferences
import de.jensklingenberg.sheasy.network.HTTPServerService
import kotlinx.android.synthetic.main.fragment_server.*
import javax.inject.Inject


class ServerFragment : BaseFragment() {

    init {
        initializeDagger()
    }

    private fun initializeDagger() = App.appComponent.inject(this)

    @Inject
    lateinit var sheasyPreferences: SheasyPreferences


    override fun getLayoutId(): Int = R.layout.fragment_server

    companion object {
        @JvmStatic
        fun newInstance() = ServerFragment()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val mViewModel = ViewModelProviders.of(this).get(NewCommonViewModel::class.java)

        serverSw.setOnCheckedChangeListener { _, isChecked ->
            when (isChecked) {
                true -> {
                    mViewModel.startService(Intent(activity, HTTPServerService::class.java))

                }
                false -> {
                    mViewModel.stopService(Intent(activity, HTTPServerService::class.java))

                }
            }
        }

        filesSw.setOnCheckedChangeListener { _, isChecked ->
            when (isChecked) {
                true -> {
                    sheasyPreferences.changePermission(MyPermission.READ_FILES, true)

                }
                false -> {
                    sheasyPreferences.changePermission(MyPermission.READ_FILES, false)

                }
            }
        }

    }


}

inline fun <reified T : Fragment> newFragmentInstance(vararg params: Pair<String, Any>) =
    T::class.java.newInstance().apply {
        arguments = bundleOf(*params)
    }