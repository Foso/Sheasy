package de.jensklingenberg.sheasy.ui.about


import android.view.View
import de.jensklingenberg.sheasy.R
import de.jensklingenberg.sheasy.ui.common.SimpleRvAdapter

import kotlinx.android.synthetic.main.list_item_file.view.*
import de.jensklingenberg.sheasy.model.AppInfo

class AboutAdapter : SimpleRvAdapter() {
    private val list = ArrayList<AboutItem>()

    var onEntryClickListener: OnEntryClickListener? = null


    fun setItems(tagArrayList: List<AboutItem>) {

        this.list.clear()
        this.list.addAll(tagArrayList)
        notifyDataSetChanged()
    }

    override fun getLayoutId() = R.layout.list_item_apps

    override fun setupDefaultTagViewHolder(holder: DefaultTagViewHolder, position: Int) {
        val item = list[position]
        holder.itemView.eventName.text = item.first
        holder.itemView.eventText.text = item.second

    }

    override fun getItemCount(): Int {
        return list.size
    }


    interface OnEntryClickListener {
        fun onTagClicked(filePath: String)
        fun onItemClicked(view: View, tag: AppInfo)
    }


}