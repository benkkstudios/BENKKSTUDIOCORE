package com.benkkstudio.core

import android.content.Context
import android.content.SharedPreferences
import com.google.gson.Gson

class BeePrefs(context: Context, name: String?) {
    val sharedPreferences: SharedPreferences by lazy {
        context.getSharedPreferences(name ?: javaClass.simpleName, Context.MODE_PRIVATE)
    }

    fun contains(key: String): Boolean {
        return sharedPreferences.contains(key)
    }

    inline fun <reified T : Any> get(key: String, defaultValue: T? = null): T? {
        with(sharedPreferences) {
            if (contains(key)) {
                return when (T::class) {
                    Boolean::class -> getBoolean(key, defaultValue as? Boolean? ?: false) as T
                    Float::class -> getFloat(key, defaultValue as? Float? ?: 0.0f) as T
                    Int::class -> getInt(key, defaultValue as? Int? ?: 0) as T
                    Long::class -> getLong(key, defaultValue as? Long? ?: 0L) as T
                    String::class -> getString(key, defaultValue as? String? ?: "") as T
                    else -> {
                        if (defaultValue is Set<*>) {
                            getStringSet(key, defaultValue as Set<String>) as T
                        } else {
                            try {
                                return Gson().fromJson(getString(key, null), T::class.java)
                            } catch (e: Exception) {
                                throw Exception("class type not supported, please call getObject")
                            }
                        }
                    }
                }
            }
        }
        return null
    }

    inline fun <reified T : Any> set(key: String, value: T) {
        with(sharedPreferences.edit()) {
            when (T::class) {
                Boolean::class -> putBoolean(key, value as Boolean)
                Float::class -> putFloat(key, value as Float)
                Int::class -> putInt(key, value as Int)
                Long::class -> putLong(key, value as Long)
                String::class -> putString(key, value as String)
                else -> {
                    if (value is Set<*>) {
                        putStringSet(key, value as Set<String>)
                    } else {
                        val json = Gson().toJson(value)
                        putString(key, json)
                    }
                }
            }
            commit()
        }
    }
}