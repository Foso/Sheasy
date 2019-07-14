package de.jensklingenberg.sheasy.ui.common

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import de.jensklingenberg.sheasy.ui.MainActivity


open class BaseFragment : Fragment() {

    open fun getLayoutId(): Int {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(getLayoutId(), container, false)
    }

    fun getBaseActivity() = activity as MainActivity
}