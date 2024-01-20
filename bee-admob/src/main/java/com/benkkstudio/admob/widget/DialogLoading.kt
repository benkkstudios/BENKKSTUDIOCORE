package com.benkkstudio.admob.widget

import android.content.Context
import android.os.Handler
import android.os.Looper
import androidx.annotation.LayoutRes
import androidx.appcompat.app.AlertDialog
import com.benkkstudio.admob.R


internal class DialogLoading(context: Context, private val loadingTime: Long = 0L, @LayoutRes customLayout: Int = R.layout.dialog_progress) {
    private var dialog: AlertDialog

    init {
        val builder = AlertDialog.Builder(context)
        builder.setView(customLayout)
        dialog = builder.create()
    }

    fun showAndDismiss(callback: () -> Unit) {
        try {
            if (!dialog.isShowing) dialog.show()
            dismiss(callback)
        } catch (e: Exception) {
            callback.invoke()
            e.printStackTrace()
        }
    }

    private fun dismiss(callback: () -> Unit) {
        Handler(Looper.getMainLooper()).postDelayed({
            if (dialog.isShowing) dialog.dismiss()
            callback.invoke()
        }, loadingTime)
    }
}