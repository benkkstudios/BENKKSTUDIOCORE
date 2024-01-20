package com.benkkstudio.coresample

import com.benkkstudio.core.BeeCore
import com.benkkstudio.core.CoreSettings

class App : BeeCore() {
    override fun setupCoreSettings(): CoreSettings {
        return CoreSettings.Builder()
            .prefName(packageName)
            .baseUrl("https://benkkstudios.xyz/sampah/")
            .enableLogging(true)
            .enableOfflineMode(false)
            .customLoadingView(R.layout.dialog_custom_loading)
            .build()
    }
}