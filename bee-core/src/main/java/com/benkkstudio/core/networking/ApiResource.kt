package com.benkkstudio.core.networking

data class ApiResource<out T>(val status: ApiStatus, val data: T?) {
    companion object {
        fun <T> success(data: T?): ApiResource<T> {
            return ApiResource(ApiStatus.SUCCESS, data)
        }

        fun <T> error(data: T? = null): ApiResource<T> {
            return ApiResource(ApiStatus.ERROR, data)
        }

        fun <T> loading(data: T? = null): ApiResource<T> {
            return ApiResource(ApiStatus.LOADING, data)
        }
    }
}