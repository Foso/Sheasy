package de.jensklingenberg.sheasy.ui.common

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import de.jensklingenberg.sheasy.App


open class BaseFragment : Fragment() {

    init {
        initializeDagger()
    }

    private fun initializeDagger() = App.appComponent.inject(this)


    open fun getLayoutId(): Int {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(getLayoutId(), container, false)
    }
}