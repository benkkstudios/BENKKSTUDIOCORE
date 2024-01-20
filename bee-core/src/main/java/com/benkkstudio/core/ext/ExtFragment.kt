package com.benkkstudio.core.ext

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment

fun Fragment.checkInternetConnectivity(): Boolean {
    return requireContext().checkInternetConnectivity()
}

fun Fragment.startAct(clazz: Class<*>) {
    requireContext().startActivity(Intent(requireContext(), clazz))
}

fun Fragment.startAct(clazz: Class<*>, bundle: Bundle) {
    Intent(requireContext(), clazz).apply {
        putExtras(bundle)
        requireContext().startActivity(this)
    }
}