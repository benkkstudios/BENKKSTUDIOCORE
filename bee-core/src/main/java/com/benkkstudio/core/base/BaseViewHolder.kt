package com.benkkstudio.core.base

import androidx.recyclerview.widget.RecyclerView
import androidx.viewbinding.ViewBinding

open class BaseViewHolder<V : ViewBinding>(val binding: V) : RecyclerView.ViewHolder(binding.root)
