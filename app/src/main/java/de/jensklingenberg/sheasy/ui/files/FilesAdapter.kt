package de.jensklingenberg.sheasy.ui.files


import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import de.jensklingenberg.sheasy.R
import kotlinx.android.synthetic.main.list_item_file.view.*
import model.FileResponse

/**
 * Created by jens on 25/2/18.
 */
class FilesAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    private val list = ArrayList<FileResponse>()

    var onEntryClickListener: OnEntryClickListener? = null


    fun setItems(tagArrayList: List<FileResponse>) {

        this.list.clear()
        this.list.addAll(tagArrayList)
        notifyDataSetChanged()
    }

    fun getLayoutId() = R.layout.list_item_file

    fun setupDefaultTagViewHolder(holder: DefaultTagViewHolder, position: Int) {
        val item = list[position]
        holder.itemView.apply {
            eventName.text = item.name
            eventText.text = item.path
            setOnClickListener {
                onEntryClickListener?.onItemClicked(item)
            }
        }


    }

    override fun getItemCount(): Int = list.size


    interface OnEntryClickListener {
        fun onItemClicked(item: FileResponse)
    }


    class DefaultTagViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val context = parent.context
        val inflater = LayoutInflater.from(context)

        when (viewType) {
            0 -> {
                val itemView = inflater.inflate(getLayoutId(), parent, false)

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

    override fun getItemViewType(position: Int): Int {
        return 0
    }


}