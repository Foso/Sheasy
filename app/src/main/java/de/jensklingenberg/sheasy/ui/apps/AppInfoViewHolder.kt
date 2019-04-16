package de.jensklingenberg.sheasy.ui.apps

import android.content.pm.PackageManager
import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.PopupMenu
import com.jakewharton.rxbinding3.appcompat.itemClicks
import de.jensklingenberg.sheasy.App
import de.jensklingenberg.sheasy.R
import de.jensklingenberg.sheasy.model.AppInfo
import de.jensklingenberg.sheasy.model.FileResponse
import de.jensklingenberg.sheasy.ui.common.BaseViewHolder
import de.jensklingenberg.sheasy.utils.extension.requireView
import io.reactivex.android.schedulers.AndroidSchedulers
import kotlinx.android.synthetic.main.list_item_generic.view.*
import javax.inject.Inject

class AppInfoViewHolder(viewParent: ViewGroup) :
    BaseViewHolder<AppInfoSourceItem>(viewParent, R.layout.list_item_app) {

    @Inject
    lateinit var pm: PackageManager

    init {
        initializeDagger()
    }

    private fun initializeDagger() = App.appComponent.inject(this)


    override fun onBindViewHolder(item2: Any, diff: Bundle) {

        val appInfo = (item2 as AppInfoSourceItem).getPayload()

        appInfo?.let {
            itemView.apply {

                title.text = it.name
                caption.text = it.packageName
                icon.setImageDrawable(pm.getApplicationIcon(it.packageName))

                moreBtn.visibility = View.VISIBLE
                moreBtn.setOnClickListener {

                    val popup = PopupMenu(it.context, it)
                        .apply {
                            menuInflater
                                .inflate(R.menu.apps_actions, menu)
                        }
                        .also {
                            it.itemClicks()
                                .subscribeOn(AndroidSchedulers.mainThread())
                                .observeOn(AndroidSchedulers.mainThread())
                                .doOnNext { menuItem ->
                                    when (menuItem.itemId) {
                                        R.id.menu_share -> {
                                            item2.onEntryClickListener?.onPopupMenuClicked(appInfo,menuItem.itemId)
                                            //presenter.shareApp(appInfo)
                                        }
                                        R.id.menu_extract -> {
                                            item2.onEntryClickListener?.onPopupMenuClicked(appInfo,menuItem.itemId)

                                        }
                                    }
                                }.subscribe()
                        }
                    popup.show()
                }
            }
        }
    }

    interface OnClick{
        fun onPopupMenuClicked(appInfo:  AppInfo, id:Int)


    }
}