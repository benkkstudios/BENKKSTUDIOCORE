package com.benkkstudio.admob.widget

import android.app.Dialog
import android.content.Context
import android.os.Handler
import android.os.Looper
import android.view.Window
import androidx.annotation.LayoutRes
import androidx.appcompat.app.AlertDialog
import com.benkkstudio.admob.R


internal class DialogLoading(context: Context, private val loadingTime: Long = 0L, @LayoutRes customLayout: Int = R.layout.dialog_progress) :
    Dialog(context, R.style.WideDialog) {


    init {
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        window!!.setBackgroundDrawableResource(android.R.color.transparent)
        setContentView(customLayout)
        setCancelable(false)
    }

    fun showAndDismiss(callback: () -> Unit) {
        try {
            if (!isShowing) show()
            dismiss(callback)
        } catch (e: Exception) {
            callback.invoke()
            e.printStackTrace()
        }
    }

    private fun dismiss(callback: () -> Unit) {
        Handler(Looper.getMainLooper()).postDelayed({
            if (isShowing) dismiss()
            callback.invoke()
        }, loadingTime)
    }
}