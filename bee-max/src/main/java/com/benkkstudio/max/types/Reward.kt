package com.benkkstudio.max.types

import android.app.Activity
import android.os.Handler
import android.os.Looper
import com.applovin.mediation.MaxAd
import com.applovin.mediation.MaxError
import com.applovin.mediation.MaxReward
import com.applovin.mediation.MaxRewardedAdListener
import com.applovin.mediation.ads.MaxRewardedAd
import com.benkkstudio.max.BeeMax
import java.util.concurrent.TimeUnit
import kotlin.math.pow

internal object Reward {
    private var rewardedAd: MaxRewardedAd? = null
    private var retryAttempt = 0.0
    private var rewardCallback: (() -> Unit?)? = null

    fun load(activity: Activity, rewardId: String) {
        rewardedAd = MaxRewardedAd.getInstance(rewardId, activity)
        rewardedAd?.setListener(object : MaxRewardedAdListener {
            override fun onAdLoaded(ad: MaxAd) {
                retryAttempt = 0.0
            }

            override fun onAdDisplayed(ad: MaxAd) {
            }

            override fun onAdHidden(ad: MaxAd) {
            }

            override fun onAdClicked(ad: MaxAd) {
            }

            override fun onAdLoadFailed(adUnitId: String, error: MaxError) {
                retryAttempt++
                val delayMillis = TimeUnit.SECONDS.toMillis(2.0.pow(6.0.coerceAtMost(retryAttempt)).toLong())
                Handler(Looper.myLooper()!!).postDelayed({ rewardedAd?.loadAd() }, delayMillis)
                BeeMax.logging("InterstitialAd : " + error.message)
                BeeMax.logging("InterstitialAd : " + error.code)
            }

            override fun onAdDisplayFailed(ad: MaxAd, error: MaxError) {
                rewardedAd?.loadAd()
                BeeMax.logging("InterstitialAd : " + error.message)
                BeeMax.logging("InterstitialAd : " + error.code)
            }

            @Deprecated("Deprecated in Java")
            override fun onRewardedVideoStarted(ad: MaxAd) {
            }

            @Deprecated("Deprecated in Java")
            override fun onRewardedVideoCompleted(ad: MaxAd) {

            }

            override fun onUserRewarded(ad: MaxAd, reward: MaxReward) {
                rewardCallback?.invoke()
                rewardedAd?.loadAd()
            }

        })
        rewardedAd?.loadAd()
    }


    fun show(activity: Activity, rewardId: String, callback: (() -> Unit)? = null) {
        if (rewardedAd == null) {
            callback?.invoke()
            load(activity, rewardId)
            return
        }
        rewardedAd?.let {
            if (it.isReady) {
                it.showAd()
            } else {
                rewardCallback?.invoke()
                load(activity, rewardId)
            }
        }
    }
}