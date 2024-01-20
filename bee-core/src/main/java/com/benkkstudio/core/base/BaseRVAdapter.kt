package com.benkkstudio.core.base

import android.annotation.SuppressLint
import android.content.Context
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.recyclerview.widget.RecyclerView
import androidx.viewbinding.ViewBinding

@Suppress("MemberVisibilityCanBePrivate", "unused")
abstract class BaseRVAdapter<T, V : ViewBinding> @JvmOverloads constructor(data: ArrayList<T>? = null) :
    RecyclerView.Adapter<BaseViewHolder<V>>() {
    var data: ArrayList<T> = data ?: arrayListOf()
    var arrayListSearch: ArrayList<T> = data ?: arrayListOf()
    lateinit var holder: BaseViewHolder<V>
    lateinit var context: Context
    protected abstract fun viewHolder(parent: ViewGroup): BaseViewHolder<V>

    override fun onCreateViewHolder(
        viewGroup: ViewGroup,
        @LayoutRes layout: Int
    ): BaseViewHolder<V> {
        context = viewGroup.context
        return viewHolder(viewGroup)
    }

    override fun onBindViewHolder(viewHolder: BaseViewHolder<V>, position: Int) {
        holder = viewHolder
        convert(viewHolder.binding, data[position], position)
    }

    abstract fun convert(binding: V, item: T, position: Int)

    @SuppressLint("NotifyDataSetChanged")
    open fun setupData(list: ArrayList<T>?) {
        if (list === this.data) {
            return
        }
        this.data.clear()
        this.data = list ?: arrayListOf()
        arrayListSearch.addAll(data)
        notifyDataSetChanged()
    }

    @SuppressLint("NotifyDataSetChanged")
    fun addMoreData(data: ArrayList<T>) {
        this.data.addAll(data)
        notifyItemRangeInserted(this.data.size, data.size)
    }

    override fun getItemCount(): Int {
        return data.size
    }

    @SuppressLint("NotifyDataSetChanged")
    open fun addData(list: ArrayList<T>, index: Int) {
        this.data.addAll(list)
        notifyDataSetChanged()
    }


    @SuppressLint("NotifyDataSetChanged")
    open fun addData(item: T) {
        this.data.add(0, item)
        notifyItemInserted(0)
        notifyDataSetChanged()
    }

    @SuppressLint("NotifyDataSetChanged")
    open fun removeData(index: Int) {
        index.let {
            this.data.removeAt(it)
            notifyItemRemoved(it)
            notifyDataSetChanged()
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    open fun clearData() {
        data.clear()
        notifyDataSetChanged()
    }
}