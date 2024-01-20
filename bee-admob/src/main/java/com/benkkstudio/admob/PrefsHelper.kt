package com.benkkstudio.admob

import android.content.Context
import android.content.Context.MODE_PRIVATE
import android.content.SharedPreferences
import com.google.gson.Gson

internal val Context.prefsHelper: SharedPreferences
    get() = getSharedPreferences(
        "BeeAdmob-prefs",
        MODE_PRIVATE
    )

internal inline fun <reified T : Any> SharedPreferences.getObject(key: String): T? {
    return Gson().fromJson<T>(getString(key, null), T::class.java)
}

@Suppress("UNCHECKED_CAST")
internal inline fun <reified T : Any> SharedPreferences.get(key: String, defaultValue: T? = null): T {
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
                    return Gson().fromJson<T>(getString(key, null), T::class.java)
                } catch (e: Exception) {
                    val typeName = T::class.java.simpleName
                    throw Error("Unable to get shared preference with value type '$typeName'. Use getObject")
                }
            }
        }
    }
}

@Suppress("UNCHECKED_CAST")
internal inline fun <reified T : Any> SharedPreferences.set(key: String, value: T) {
    with(edit()) {
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