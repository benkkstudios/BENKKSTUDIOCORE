package com.benkkstudio.coresample

import android.app.Activity
import com.benkkstudio.admob.BeeAdmob


class AdsUtil(activity: Activity) {
    companion object {
        @Volatile
        private var instance: AdsUtil? = null
        fun instance(activity: Activity): AdsUtil {
            if (instance == null) {
                instance = AdsUtil(activity)
            }
            return instance!!
        }
    }
    
}