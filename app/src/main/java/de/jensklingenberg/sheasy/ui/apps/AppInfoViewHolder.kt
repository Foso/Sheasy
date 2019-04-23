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
import de.jensklingenberg.sheasy.ui.common.BaseViewHolder
import io.reactivex.android.schedulers.AndroidSchedulers
import kotlinx.android.synthetic.main.list_item_generic.view.*
import javax.inject.Inject

class AppInfoViewHolder(viewParent: ViewGroup) :
    BaseViewHolder<AppInfoSourceItem>(viewParent, R.layout.list_item_app) {

    @Inject
    lateinit var packageManager: PackageManager

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
                icon.setImageDrawable(appInfo.drawable)

                moreBtn.visibility = View.VISIBLE
                moreBtn.setOnClickListener {

                    val popup = setupContextMenu(it, item2, appInfo)
                    popup.show()
                }
            }
        }
    }

    private fun setupContextMenu(
        it: View,
        item2: AppInfoSourceItem,
        appInfo: AppInfo
    ): PopupMenu {
      return  PopupMenu(it.context, it)
            .apply {
                menuInflater
                    .inflate(R.menu.apps_actions, menu)
            }
            .also {
                it.itemClicks()
                    .subscribeOn(AndroidSchedulers.mainThread())
                    .observeOn(AndroidSchedulers.mainThread())
                    .doOnNext { menuItem ->
                        item2.onEntryClickListener?.onPopupMenuClicked(appInfo, menuItem.itemId)
                    }.subscribe()
            }
    }

    interface OnClick {
        fun onPopupMenuClicked(appInfo: AppInfo, id: Int)


    }
}