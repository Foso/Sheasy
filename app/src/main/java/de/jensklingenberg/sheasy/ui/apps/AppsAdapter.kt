package de.jensklingenberg.sheasy.ui.apps


import android.support.v7.widget.PopupMenu
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import de.jensklingenberg.sheasy.R
import de.jensklingenberg.sheasy.model.AppsResponse
import de.jensklingenberg.sheasy.model.Event
import kotlinx.android.synthetic.main.list_item_apps.view.*
import android.view.MenuInflater
import de.jensklingenberg.sheasy.App


/**
 * Created by jens on 25/2/18.
 */
class AppsAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    private val list = ArrayList<AppsResponse>()
    var onTagClickListener: OnTagClickListener? = null

    fun setItems(tagArrayList: List<AppsResponse>) {
        this.list.clear()
        this.list.addAll(tagArrayList)
    }


    class DefaultTagViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val context = parent.context
        val inflater = LayoutInflater.from(context)

        when (viewType) {
            0 -> {
                val itemView = inflater.inflate(R.layout.list_item_apps, parent, false)

                return DefaultTagViewHolder(
                    itemView
                )
            }
        }

        throw RuntimeException(
            "there is no type that matches the type "
                    + viewType
                    + " + make sure your using types correctly"
        )
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {

        if (holder is DefaultTagViewHolder) {
            setupDefaultTagViewHolder(holder, position)
        }
    }

    private fun setupDefaultTagViewHolder(holder: DefaultTagViewHolder, position: Int) {
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


    override fun getItemViewType(position: Int) = 0

    override fun getItemCount() = list.size


    interface OnTagClickListener {
        fun onTagClicked(view: View, tag: AppsResponse)
    }


}