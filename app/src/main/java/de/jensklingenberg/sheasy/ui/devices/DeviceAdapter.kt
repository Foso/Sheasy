package de.jensklingenberg.sheasy.ui.devices


import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import de.jensklingenberg.sheasy.R
import de.jensklingenberg.sheasy.model.ConnectionInfo
import de.jensklingenberg.sheasy.model.Event
import de.jensklingenberg.sheasy.model.FileResponse
import de.jensklingenberg.sheasy.ui.common.SimpleRvAdapter
import kotlinx.android.synthetic.main.list_item_event.view.*

/**
 * Created by jens on 25/2/18.
 */
class DeviceAdapter : SimpleRvAdapter() {
    private val list = ArrayList<ConnectionInfo>()

    var onEntryClickListener: OnEntryClickListener? = null


    fun setItems(tagArrayList: List<ConnectionInfo>) {
        this.list.clear()
        this.list.addAll(tagArrayList)
        notifyDataSetChanged()
    }

    override fun getLayoutId() = R.layout.list_item_file

    override fun setupDefaultTagViewHolder(holder: DefaultTagViewHolder, position: Int) {
        val item = list[position]
        holder.itemView.eventName.text = item.deviceName
        holder.itemView.eventText.text = item.result
        // holder.itemView.setOnClickListener { onEntryClickListener?.onTagClicked(item.path) }

    }


    override fun getItemCount(): Int {
        return list.size
    }

    interface OnEntryClickListener {
        fun onTagClicked(filePath: String)
    }


}