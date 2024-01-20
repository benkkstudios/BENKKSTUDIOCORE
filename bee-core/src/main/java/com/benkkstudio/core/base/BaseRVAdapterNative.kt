package com.benkkstudio.core.base

import android.annotation.SuppressLint
import android.content.Context
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.recyclerview.widget.RecyclerView
import androidx.viewbinding.ViewBinding

@Suppress("MemberVisibilityCanBePrivate", "unused", "UNCHECKED_CAST")
abstract class BaseRVAdapterNative<T, M : ViewBinding, N : ViewBinding> : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    var data: ArrayList<T?> = arrayListOf()
    lateinit var context: Context
    private var interval: Int = 3
    private var showFirst: Boolean = false
    protected abstract fun mainHolder(parent: ViewGroup): BaseViewHolder<M>
    protected abstract fun nativeHolder(parent: ViewGroup): BaseViewHolder<N>
    override fun onCreateViewHolder(
        viewGroup: ViewGroup,
        @LayoutRes layout: Int
    ): RecyclerView.ViewHolder {
        context = viewGroup.context
        return if (layout == 0) {
            mainHolder(viewGroup)
        } else {
            nativeHolder(viewGroup)
        }
    }

    override fun onBindViewHolder(viewHolder: RecyclerView.ViewHolder, position: Int) {
        if (viewHolder.itemViewType == 0) {
            viewHolder as BaseViewHolder<M>
            convert(viewHolder.binding, data[position], position)
        } else {
            viewHolder as BaseViewHolder<N>
            native(viewHolder.binding, data[position], position)
        }
    }

    abstract fun convert(binding: M, item: T?, position: Int)

    abstract fun native(binding: N, item: T?, position: Int)

    override fun getItemViewType(position: Int): Int {
        return if (data[position] != null) {
            0
        } else {
            1
        }
    }

    open fun setupInterval(interval: Int) {
        this.interval = interval
    }

    open fun showFirst(showFirst: Boolean) {
        this.showFirst = showFirst
    }

    @SuppressLint("NotifyDataSetChanged")
    open fun setupData(list: ArrayList<T>) {
        this.data.clear()
        for (i in 0 until list.size) {
            if (showFirst) {
                if (i % interval == 0) {
                    this.data.add(null)
                }
            } else {
                if (i % interval == 0 && i != 0) {
                    this.data.add(null)
                }
            }
            this.data.add(list[i])
        }
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int {
        return data.size
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
