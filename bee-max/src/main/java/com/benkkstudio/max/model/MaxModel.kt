package com.benkkstudio.max.model

import com.benkkstudio.max.R

data class MaxModel(
    var debugMode: Boolean = false,
    var enableLogging: Boolean = false,
    var interstitialId: String = "",
    var bannerId: String = "",
    var rewardId: String = "",
    var nativeId: String = "",
    var withLoading: Boolean = false,
    var interstitialInterval: Int? = 0,
    var customLadingView: Int = R.layout.dialog_progress
)