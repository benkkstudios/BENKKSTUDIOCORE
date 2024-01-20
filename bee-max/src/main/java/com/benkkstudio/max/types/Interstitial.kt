package com.benkkstudio.max.types

import android.app.Activity
import android.os.Handler
import android.os.Looper
import com.applovin.mediation.MaxAd
import com.applovin.mediation.MaxAdListener
import com.applovin.mediation.MaxError
import com.applovin.mediation.ads.MaxInterstitialAd
import com.benkkstudio.max.BeeMax
import java.util.concurrent.TimeUnit
import kotlin.math.pow

internal object Interstitial {
    private var interstitialAd: MaxInterstitialAd? = null
    private var interstitialCallback: (() -> Unit?)? = null
    private var retryAttemptInterstitial = 0.0

    fun load(activity: Activity, interstitialId: String) {
        interstitialAd = MaxInterstitialAd(interstitialId, activity)
        interstitialAd!!.setListener(object : MaxAdListener {
            override fun onAdLoaded(ad: MaxAd) {
                retryAttemptInterstitial = 0.0
            }

            override fun onAdDisplayed(ad: MaxAd) {
            }

            override fun onAdHidden(ad: MaxAd) {
                interstitialCallback?.invoke()
                interstitialAd?.loadAd()
            }

            override fun onAdClicked(ad: MaxAd) {
            }

            override fun onAdLoadFailed(adUnitId: String, error: MaxError) {
                retryAttemptInterstitial++
                val delayMillis = TimeUnit.SECONDS.toMillis(
                    2.0.pow(6.0.coerceAtMost(retryAttemptInterstitial))
                        .toLong()
                )
                Handler(Looper.myLooper()!!).postDelayed({ interstitialAd?.loadAd() }, delayMillis)
                BeeMax.logging("InterstitialAd : " + error.message)
                BeeMax.logging("InterstitialAd : " + error.code)
            }

            override fun onAdDisplayFailed(ad: MaxAd, error: MaxError) {
                interstitialCallback?.invoke()
                interstitialAd?.loadAd()
                BeeMax.logging("InterstitialAd : " + error.message)
                BeeMax.logging("InterstitialAd : " + error.code)
            }
        })
        interstitialAd!!.loadAd()
    }

    fun show(activity: Activity, interstitialId: String, callback: (() -> Unit)? = null) {
        if (interstitialAd == null) {
            callback?.invoke()
            load(activity, interstitialId)
            return
        }
        interstitialAd?.let {
            interstitialCallback = callback
            if (it.isReady) {
                it.showAd()
            } else {
                interstitialCallback?.invoke()
                load(activity, interstitialId)
            }
        }
    }
}