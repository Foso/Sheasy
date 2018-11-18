package de.jensklingenberg.sheasy.ui.apps


import android.view.View
import de.jensklingenberg.sheasy.R
import de.jensklingenberg.sheasy.ui.common.SimpleRvAdapter

import kotlinx.android.synthetic.main.list_item_file.view.*
import model.AppFile

class AppsAdapter : SimpleRvAdapter() {
    private val list = ArrayList<AppFile>()

    var onEntryClickListener: OnEntryClickListener? = null


    fun setItems(tagArrayList: List<AppFile>) {

        this.list.clear()
        this.list.addAll(tagArrayList)
        notifyDataSetChanged()
    }

    override fun getLayoutId() = R.layout.list_item_apps

    override fun setupDefaultTagViewHolder(holder: DefaultTagViewHolder, position: Int) {
        val item = list[position]
        holder.itemView.eventName.text = item.name

    }

    override fun getItemCount(): Int {
        return list.size
    }


    interface OnEntryClickListener {
        fun onTagClicked(filePath: String)
        fun onItemClicked(view: View, tag: AppFile)
    }


}