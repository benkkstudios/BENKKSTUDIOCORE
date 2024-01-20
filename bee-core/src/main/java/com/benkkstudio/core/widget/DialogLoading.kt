package com.benkkstudio.core.widget

import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.Window
import android.view.WindowManager
import com.benkkstudio.core.BeeCore
import com.benkkstudio.core.R
import com.benkkstudio.core.coreSettings

class DialogLoading(context: Context, private val cancelable: Boolean? = true) : Dialog(context, R.style.AlertDialog) {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        window?.let {
            it.addFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND)
            it.setDimAmount(0.5f)
            requestWindowFeature(Window.FEATURE_NO_TITLE)
            it.setBackgroundDrawableResource(android.R.color.transparent)
        }
        setContentView(coreSettings.customLoadingView)
        setCancelable(cancelable!!)
    }

    override fun show() {
        try {
            if (!isShowing) {
                super.show()
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    fun dismiss(delay: Long) {
        Handler(Looper.myLooper()!!).postDelayed({
            try {
                if (isShowing) {
                    dismiss()
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }, delay)
    }

    override fun dismiss() {
        try {
            if (isShowing) {
                super.dismiss()
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
}