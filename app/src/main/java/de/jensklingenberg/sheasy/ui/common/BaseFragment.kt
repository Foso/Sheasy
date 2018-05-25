package de.jensklingenberg.sheasy.ui.common

import android.support.v4.app.Fragment
import de.jensklingenberg.sheasy.data.viewmodel.ProfileViewModel
import de.jensklingenberg.sheasy.data.viewmodel.ViewModelFactory

/**
 * Created by jens on 1/4/18.
 */
open class BaseFragment : Fragment() {

    fun obtainProfileViewModel(): ProfileViewModel {
        return ViewModelFactory.obtainProfileViewModel(activity)
    }


}