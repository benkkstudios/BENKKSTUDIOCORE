package com.benkkstudio.core

import android.app.Application
import android.util.Log
import com.benkkstudio.core.networking.ApiService


val prefs = BeeCore.prefs
val apiService = BeeCore.apiService
val appInstance = BeeCore.instance()
val coreSettings = BeeCore.coreSettings
fun logging(any: Any) {
    if (BeeCore.coreSettings.enableLogging) {
        Log.e("ABENK : ", any.toString())
    }
}

abstract class BeeCore : Application() {
    companion object {
        private var instance: BeeCore? = null
        fun instance(): BeeCore {
            return instance!!
        }

        lateinit var prefs: BeePrefs
        lateinit var apiService: ApiService
        lateinit var coreSettings: CoreSettings
    }

    override fun onCreate() {
        super.onCreate()
        instance = this
        coreSettings = setupCoreSettings()
        prefs = BeePrefs(this, coreSettings.prefName)
        apiService = ApiService.initialize(this, coreSettings.baseUrl, coreSettings.enableOfflineMode)
    }

    abstract fun setupCoreSettings(): CoreSettings

}