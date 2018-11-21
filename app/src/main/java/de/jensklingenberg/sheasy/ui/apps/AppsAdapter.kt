package de.jensklingenberg.sheasy.ui.apps


import android.view.View
import de.jensklingenberg.sheasy.R
import de.jensklingenberg.sheasy.model.AppInfo
import de.jensklingenberg.sheasy.model.AppResponse
import de.jensklingenberg.sheasy.ui.common.SimpleRvAdapter
import kotlinx.android.synthetic.main.list_item_apps.view.*


class AppsAdapter : SimpleRvAdapter() {
    private val list = ArrayList<AppInfo>()

    var onEntryClickListener: OnEntryClickListener? = null


    fun setItems(tagArrayList: List<AppInfo>) {
        this.list.clear()
        this.list.addAll(tagArrayList)
        notifyDataSetChanged()
    }

    override fun getLayoutId() = R.layout.list_item_apps

    override fun setupDefaultTagViewHolder(holder: DefaultTagViewHolder, position: Int) {
        val item = list[position]
        holder.itemView.eventName.text = item.name
        holder.itemView.iconIv.setImageDrawable(item.icon)

    }

    override fun getItemCount(): Int {
        return list.size
    }


    interface OnEntryClickListener {
        fun onTagClicked(filePath: String)
        fun onItemClicked(view: View, tag: AppResponse)
    }


}