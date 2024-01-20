package com.benkkstudio.core.base

import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.view.Window
import android.view.WindowManager
import androidx.viewbinding.ViewBinding
import com.benkkstudio.core.R
import com.benkkstudio.core.ext.getBinding


@Suppress("MemberVisibilityCanBePrivate")
abstract class BaseDialog<V : ViewBinding>(context: Context, private val cancelable: Boolean? = true) :
    Dialog(context, R.style.AlertDialog) {
    lateinit var binding: V

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        window?.let {
            it.addFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND)
            it.setDimAmount(0.5f)
            requestWindowFeature(Window.FEATURE_NO_TITLE)
            it.setBackgroundDrawableResource(android.R.color.transparent)
        }
        binding = getBinding()
        setContentView(binding.root)
        setCancelable(cancelable!!)
    }
}

