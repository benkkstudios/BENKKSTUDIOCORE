package com.benkkstudio.core.ext

import android.graphics.Color
import android.os.Build
import android.os.Parcelable
import android.view.View
import android.view.WindowManager
import androidx.activity.OnBackPressedCallback
import androidx.appcompat.app.AppCompatActivity
import java.io.Serializable

fun AppCompatActivity.addOnBackPressedDispatcher(onBackPressed: () -> Unit = { finish() }) {
    onBackPressedDispatcher.addCallback(
        this,
        object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                onBackPressed.invoke()
            }
        }
    )
}

@Suppress("DEPRECATION")
fun AppCompatActivity.hideStatusBar() {
    window.apply {
        clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
        addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
        decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
        statusBarColor = Color.TRANSPARENT
    }
}

@Suppress("DEPRECATION", "UNCHECKED_CAST")
fun <T : Serializable?> AppCompatActivity.getSerializable(name: String, clazz: Class<T>): T? {
    return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU)
        intent.getSerializableExtra(name, clazz)
    else
        intent.getSerializableExtra(name) as T
}

@Suppress("DEPRECATION", "UNCHECKED_CAST")
fun <T : Parcelable?> AppCompatActivity.getParcelable(name: String, clazz: Class<T>): T? {
    return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU)
        intent.getParcelableExtra(name, clazz)
    else
        intent.getParcelableArrayExtra(name) as T
}