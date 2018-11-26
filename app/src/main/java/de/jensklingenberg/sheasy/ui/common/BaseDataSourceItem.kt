package de.jensklingenberg.sheasy.ui.common

import android.os.Bundle
import android.text.TextUtils


abstract class BaseDataSourceItem<P>(val viewTypeClass: Class<out BaseViewHolder<*>>) {
    private var payload: P? = null

    var index: Int = 0

    fun getPayload(): P? {
        return payload
    }

    fun setPayload(payload: P?): BaseDataSourceItem<*> {
        this.payload = payload
        return this
    }

    fun areItemsTheSame(other: BaseDataSourceItem<P>): Boolean {

        return isInstanceSameClass(other) && areItemsTheSameInner(other)
    }

    fun areContentsTheSame(other: BaseDataSourceItem<P>): Boolean {

        return isInstanceSameClass(other) && areContentsTheSameInner(other)
    }

    protected abstract fun areItemsTheSameInner(other: BaseDataSourceItem<P>): Boolean

    protected abstract fun areContentsTheSameInner(other: BaseDataSourceItem<P>): Boolean

    open fun getChangePayload(other: BaseDataSourceItem<P>): Bundle? {
        return if (isInstanceSameClass(other)) {

            getChangePayloadInner(other)
        } else null
    }

    private fun isInstanceSameClass(other: BaseDataSourceItem<*>): Boolean {
        return TextUtils.equals(other.javaClass.name, javaClass.name)
    }

    protected open fun getChangePayloadInner(other: BaseDataSourceItem<P>): Bundle? {
        return null
    }

    companion object {
        fun convert(listOf: List<Any>) {

        }
    }

}