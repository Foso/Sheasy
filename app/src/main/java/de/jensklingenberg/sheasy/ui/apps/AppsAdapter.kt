package de.jensklingenberg.sheasy.ui.apps


import android.view.View
import de.jensklingenberg.sheasy.R
import de.jensklingenberg.sheasy.model.AppsResponse
import kotlinx.android.synthetic.main.list_item_apps.view.*
import de.jensklingenberg.sheasy.ui.common.SimpleRvAdapter


/**
 * Created by jens on 25/2/18.
 */
class AppsAdapter : SimpleRvAdapter() {
    private val list = ArrayList<AppsResponse>()
    var onTagClickListener: OnTagClickListener? = null

    fun setItems(tagArrayList: List<AppsResponse>) {
        this.list.clear()
        this.list.addAll(tagArrayList)
    }

    override fun getLayoutId() = R.layout.list_item_apps

    override fun setupDefaultTagViewHolder(holder: DefaultTagViewHolder, position: Int) {
        val item = list[position]
        holder.itemView.eventName.text = item.name
        holder.itemView.eventText.text = item.packageName
        holder.itemView.shareBtn.setOnClickListener {
            onTagClickListener?.onTagClicked(
                holder.itemView.shareBtn,
                item
            )
        }
        holder.itemView.setOnClickListener { }

    }

    override fun getItemCount() = list.size


    interface OnTagClickListener {
        fun onTagClicked(view: View, tag: AppsResponse)
    }


}