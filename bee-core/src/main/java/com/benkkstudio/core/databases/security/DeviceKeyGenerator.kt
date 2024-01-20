package com.benkkstudio.core.databases.security

import android.annotation.SuppressLint
import android.content.Context
import android.provider.Settings.Secure

object DeviceKeyGenerator {
    @SuppressLint("HardwareIds")
    fun Generate(context: Context): String {
        return Secure.getString(context.contentResolver, Secure.ANDROID_ID)
    }

    //recommended
    @SuppressLint("HardwareIds")
    fun Generate(context: Context, secret: String): String {
        val id = Secure.getString(context.contentResolver, Secure.ANDROID_ID)
        return id.substring(secret.length) + secret
    }
}