package com.benkkstudio.core.base

open class SingletonHolderSingle<out T>(private val constructor: () -> T) {
    @Volatile
    private var instance: T? = null

    fun getInstance(): T =
        instance ?: synchronized(this) {
            instance ?: constructor().also { instance = it }
        }
}