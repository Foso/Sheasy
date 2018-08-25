package de.jensklingenberg.sheasy.ui.help

import android.arch.lifecycle.Observer
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.view.View
import android.webkit.WebViewClient
import de.jensklingenberg.sheasy.R
import de.jensklingenberg.sheasy.data.viewmodel.CommonViewModel
import de.jensklingenberg.sheasy.model.FileResponse
import de.jensklingenberg.sheasy.ui.MainActivity
import de.jensklingenberg.sheasy.ui.common.BaseFragment
import de.jensklingenberg.sheasy.ui.common.ITabView
import kotlinx.android.synthetic.main.component_toolbar_standard.*
import kotlinx.android.synthetic.main.fragment_files.*
import kotlinx.android.synthetic.main.fragment_help.*
import org.markdownj.MarkdownProcessor
import android.webkit.WebResourceRequest
import android.webkit.WebView


/**
 * Created by jens on 1/4/18.
 */
class HelpFragment : BaseFragment(), ITabView {


    lateinit var commonViewModel: CommonViewModel


    override fun getTabNameResId() = R.string.main_frag_tab_name


    override fun getLayoutId() = R.layout.fragment_help

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        test(
            "Welcome to the Sheasy wiki!``\n" +
                    "\n" +
                    "# How to build\n" +
                    "[Building the Webfrontend](https://github.com/Foso/Sheasy/wiki/Building-the-Webfrontend)\n" +
                    "\n" +
                    "[Building Swagger](https://github.com/Foso/Sheasy/wiki/Building-Swagger)"
        )


    }


    fun test(txt: String) {
        val m = MarkdownProcessor()
        var html = m.markdown(txt)
        markdownView.webViewClient = object : WebViewClient() {
            override fun shouldOverrideUrlLoading(
                view: WebView,
                request: WebResourceRequest
            ): Boolean {
                view.loadUrl(request.url.toString())
                return false
            }
        }
        markdownView.loadDataWithBaseURL("http://", html, "text/html", "UTF-8", null)
    }

    companion object {
        @JvmStatic
        fun newInstance() = HelpFragment()
    }


}