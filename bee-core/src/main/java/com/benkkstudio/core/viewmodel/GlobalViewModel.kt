package com.benkkstudio.core.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.benkkstudio.core.apiService
import com.benkkstudio.core.networking.ApiResource
import kotlinx.coroutines.Dispatchers


@Suppress("RemoveExplicitTypeArguments")
open class GlobalViewModel : ViewModel() {
    inline fun <reified T> get(
        api: String, params: HashMap<String, String>? = null, header: HashMap<String, String>? = null
    ) = liveData(Dispatchers.IO) {
        emit(ApiResource.loading(data = null))
        try {
            emit(ApiResource.success(data = apiService.get<T>(url = api, params = params, header = header)))
        } catch (exception: Exception) {
            emit(ApiResource.error(data = null))
        }
    }

    inline fun <reified T> post(
        api: String, params: HashMap<String, String>? = null, header: HashMap<String, String>? = null
    ) = liveData(Dispatchers.IO) {
        emit(ApiResource.loading(data = null))
        try {
            emit(ApiResource.success(data = apiService.post<T>(url = api, params = params, header = header)))
        } catch (exception: Exception) {
            emit(ApiResource.error(data = null))
        }
    }
}