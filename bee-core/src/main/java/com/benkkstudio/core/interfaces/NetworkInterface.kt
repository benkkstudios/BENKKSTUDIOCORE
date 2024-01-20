package com.benkkstudio.core.interfaces

interface NetworkInterface<T> {
    fun success(data: T)
    fun error()
    fun connectionError()
}