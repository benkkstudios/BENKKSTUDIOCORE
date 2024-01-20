package com.benkkstudio.core.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

@Suppress("UNCHECKED_CAST")
class ViewModelFactory() : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(GlobalViewModel::class.java)) {
            return GlobalViewModel() as T
        }
        throw IllegalArgumentException("Unknown class name")
    }
}
