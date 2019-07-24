package de.jensklingenberg.sheasy.ui.common

import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers

class BaseAdapter : RecyclerView.Adapter<BaseViewHolder<*>>() {


    val dataSource = BaseDataSource()

    private var disposable: Disposable? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder<*> {
        val viewHolderClass = dataSource.getViewHolderClassForViewType(viewType)
        if (viewHolderClass != null) {
            return viewHolderClass.getDeclaredConstructor(ViewGroup::class.java)
                .newInstance(parent)
        }
        return throw Exception("NO Viewholder")

    }

    override fun getItemCount(): Int = dataSource.size

    override fun onBindViewHolder(holder: BaseViewHolder<*>, position: Int) {
        onBindViewHolder(holder, position, emptyList())
    }

    override fun onBindViewHolder(holder: BaseViewHolder<*>, position: Int, payloads: List<Any>) {
        val item = dataSource[position]
        //determine, if the empty state should fit the height of the recycler view

        holder.onBindViewHolderInternal(item, payloads)
    }

    override fun getItemViewType(position: Int): Int {
        return dataSource.getViewTypeForPosition(position) ?: throw Exception("No viewType")
    }

    fun setItems(items: List<BaseDataSourceItem<*>>) {
        merge(items)
    }

    private fun merge(newItems: List<BaseDataSourceItem<*>>) {
        val diffSingle = Single.create<DiffUtil.DiffResult> { e ->
            e.onSuccess(
                DiffUtil.calculateDiff(
                    SourceItemDiffCallback(
                        dataSource,
                        newItems
                    )
                )
            )
        }

        disposable?.dispose()


        disposable = diffSingle
            .subscribeOn(Schedulers.computation())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe { diffResult ->
                diffResult.dispatchUpdatesTo(this)
                dataSource.clear()
                dataSource.addAll(newItems)
            }
    }

}