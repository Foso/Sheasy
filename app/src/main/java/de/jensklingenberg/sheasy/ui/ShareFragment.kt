package de.jensklingenberg.sheasy.ui

import android.arch.lifecycle.Observer
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import de.jensklingenberg.sheasy.R
import de.jensklingenberg.sheasy.data.viewmodel.ProfileViewModel
import de.jensklingenberg.sheasy.data.viewmodel.ViewModelFactory
import de.jensklingenberg.sheasy.ui.common.BaseFragment
import kotlinx.android.synthetic.main.activity_share_actvity.*

/**
 * Created by jens on 1/4/18.
 */
class ShareFragment : BaseFragment() {

    lateinit var profileViewModel: ProfileViewModel


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        return inflater.inflate(R.layout.activity_share_actvity, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        profileViewModel = ViewModelFactory.obtainProfileViewModel(activity)



        profileViewModel.sharedFolder.observe(this, Observer {
            fileTv?.text = it

        })

    }


    companion object {
        @JvmStatic
        fun newInstance(): ShareFragment {
            return ShareFragment()
        }
    }

}