package com.benkkstudio.core.networking

import android.content.Context
import com.benkkstudio.core.fan.AndroidNetworking
import com.benkkstudio.core.fan.common.ANRequest
import com.benkkstudio.core.prefs
import com.google.gson.Gson
import com.google.gson.JsonArray
import com.google.gson.JsonObject
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import okhttp3.OkHttpClient
import java.util.concurrent.TimeUnit


class ApiService(val baseUrl: String, val enableOfflineMode: Boolean) {
    companion object {
        private var instance: ApiService? = null
        fun initialize(context: Context, baseUrl: String, enableOfflineMode: Boolean): ApiService {
            if (instance == null) {
                val client = OkHttpClient.Builder()
                    .connectTimeout(20, TimeUnit.SECONDS)
                    .readTimeout(20, TimeUnit.SECONDS)
                    .writeTimeout(20, TimeUnit.SECONDS).build()
                AndroidNetworking.initialize(context, client)
                instance = ApiService(baseUrl, enableOfflineMode)
            }
            return instance!!
        }
    }


    suspend inline fun <reified T> get(
        url: String,
        params: HashMap<String, String>? = null,
        header: HashMap<String, String>? = null,
    ): ApiResource<T> {

        val urls = baseUrl + url
        val request = AndroidNetworking.get(urls)
            .addQueryParameter(params)
            .addHeaders(header)
            .build()
        return request.fetch(urls, params)
    }


    suspend inline fun <reified T> post(
        url: String,
        params: HashMap<String, String>? = null,
        header: HashMap<String, String>? = null
    ): ApiResource<T> {
        val urls = baseUrl + url
        val request = AndroidNetworking.post(urls)
            .addBodyParameter(params)
            .addHeaders(header)
            .build()
        return request.fetch(urls, params)
    }

    suspend inline fun <reified T> ANRequest<*>.fetch(url: String, params: HashMap<String, String>? = null): ApiResource<T> =
        withContext(Dispatchers.IO) {
            try {
                val response = executeForString()
                if (response.isSuccess) {
                    return@withContext fetchOnline<T>(url, response.result.toString(), params)
                } else {
                    if (!enableOfflineMode) {
                        return@withContext ApiResource.error()
                    }
                    try {
                        return@withContext fetchOffline<T>(url, params)
                    } catch (e: Exception) {
                        return@withContext ApiResource.error()
                    }
                }

            } catch (e: Exception) {
                e.printStackTrace()
                if (!enableOfflineMode) {
                    return@withContext ApiResource.error()
                }
                try {
                    return@withContext fetchOffline<T>(url, params)
                } catch (e: Exception) {
                    return@withContext ApiResource.error()
                }
            }
        }

    suspend inline fun <reified T> fetchOnline(url: String, result: String, params: HashMap<String, String>? = null): ApiResource<T> =
        withContext(Dispatchers.IO) {
            if (enableOfflineMode) {
                prefs.set(url + params.toString(), result)
            }
            when (T::class.java) {
                String::class.java -> {
                    return@withContext ApiResource.success<T>(result as T)
                }

                JsonObject::class.java -> {
                    val jsonObject = Gson().fromJson(result, JsonObject::class.java)
                    return@withContext ApiResource.success<T>(jsonObject as T)
                }

                JsonArray::class.java -> {
                    val jsonArray = Gson().fromJson(result, JsonArray::class.java)
                    return@withContext ApiResource.success<T>(jsonArray as T)
                }

                else -> {
                    val model = Gson().fromJson(result, T::class.java)
                    return@withContext ApiResource.success<T>(model as T)
                }
            }
        }

    suspend inline fun <reified T> fetchOffline(url: String, params: HashMap<String, String>? = null): ApiResource<T> =
        withContext(Dispatchers.IO) {
            val result = withContext(Dispatchers.Main) {
                prefs.get<String>(url + params.toString())
            } ?: return@withContext ApiResource.success<T>(null)
            when (T::class.java) {
                String::class.java -> {
                    return@withContext ApiResource.success<T>(result as T)
                }

                JsonObject::class.java -> {
                    val jsonObject = Gson().fromJson(result, JsonObject::class.java)
                    return@withContext ApiResource.success<T>(jsonObject as T)
                }

                JsonArray::class.java -> {
                    val jsonArray = Gson().fromJson(result, JsonArray::class.java)
                    return@withContext ApiResource.success<T>(jsonArray as T)
                }

                else -> {
                    val model = Gson().fromJson(result, T::class.java)
                    return@withContext ApiResource.success<T>(model as T)
                }
            }
        }
}
