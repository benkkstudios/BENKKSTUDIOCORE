package com.benkkstudio.core.ext

import android.view.View

fun View.click(callback: (View) -> Unit) {
    setOnClickListener {
        callback.invoke(this)
    }
}

fun View.show() {
    visibility = View.VISIBLE
}

fun View.hide() {
    visibility = View.GONE
}