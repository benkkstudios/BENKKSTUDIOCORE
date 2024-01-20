package com.benkkstudio.max.types

import android.R
import android.app.Activity
import android.widget.FrameLayout
import com.applovin.mediation.MaxAd
import com.applovin.mediation.MaxAdRevenueListener
import com.applovin.mediation.MaxError
import com.applovin.mediation.nativeAds.MaxNativeAdListener
import com.applovin.mediation.nativeAds.MaxNativeAdLoader
import com.applovin.mediation.nativeAds.MaxNativeAdView
import com.benkkstudio.max.BeeMax


interface NativeListener {
    fun onLoaded(nativeAd: MaxNativeAdView)
    fun onFailed()
}

internal object Native : MaxNativeAdListener() {
    private var nativeAdLoader: MaxNativeAdLoader? = null
    private val listNative = arrayListOf<MaxNativeAdView>()

    fun load(activity: Activity, nativeId: String, nativeListener: NativeListener) {
        nativeAdLoader = MaxNativeAdLoader(nativeId, activity)
        nativeAdLoader!!.setNativeAdListener(object : MaxNativeAdListener() {
            override fun onNativeAdLoaded(nativeAdView: MaxNativeAdView?, ad: MaxAd) {
                nativeAdView?.let {
                    listNative.add(it)
                    nativeListener.onLoaded(listNative.random())
                }
            }

            override fun onNativeAdLoadFailed(adUnitId: String, error: MaxError) {
                nativeListener.onFailed()
                BeeMax.logging("InterstitialAd : " + error.message)
                BeeMax.logging("InterstitialAd : " + error.code)
            }

            override fun onNativeAdClicked(ad: MaxAd) {
            }
        })
        nativeAdLoader!!.loadAd()
    }

    fun getNative(activity: Activity, nativeId: String, nativeListener: NativeListener) {
        if (listNative.isEmpty()) {
            load(activity, nativeId, nativeListener)
        } else {
            nativeListener.onLoaded(listNative.random())
        }
    }
}