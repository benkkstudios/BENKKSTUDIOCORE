package com.benkkstudio.core

import androidx.annotation.LayoutRes

class CoreSettings(
    var prefName: String = "BENKKSTUDIO",
    var baseUrl: String = "http://google.com",
    var enableOfflineMode: Boolean = false,
    var enableLogging: Boolean = false,
    @LayoutRes var customLoadingView: Int = R.layout.base_dialog_loading,
) {

    private constructor(builder: Builder) : this(builder.prefName, builder.baseUrl, builder.enableOfflineMode, builder.enableLogging, builder.customLoadingView)

    class Builder {
        var prefName: String = "BENKKSTUDIO"
            private set
        var baseUrl: String = "http://google.com"
            private set
        var enableOfflineMode: Boolean = false
            private set
        var enableLogging: Boolean = false
            private set
        var customLoadingView: Int = R.layout.base_dialog_loading
            private set

        fun prefName(prefName: String) = apply { this.prefName = prefName }
        fun baseUrl(baseUrl: String) = apply { this.baseUrl = baseUrl }

        fun enableOfflineMode(enableOfflineMode: Boolean) = apply { this.enableOfflineMode = enableOfflineMode }
        fun enableLogging(enableLogging: Boolean) = apply { this.enableLogging = enableLogging }

        fun customLoadingView(customLoadingView: Int) = apply { this.customLoadingView = customLoadingView }
        fun build() = CoreSettings(this)
    }
}