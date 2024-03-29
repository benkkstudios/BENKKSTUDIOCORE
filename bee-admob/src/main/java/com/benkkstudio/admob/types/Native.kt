package com.benkkstudio.admob.types

import android.app.Activity
import com.benkkstudio.admob.BeeAdRequest
import com.benkkstudio.admob.BeeAdmob
import com.google.android.gms.ads.AdListener
import com.google.android.gms.ads.AdLoader
import com.google.android.gms.ads.LoadAdError
import com.google.android.gms.ads.nativead.NativeAd

interface NativeListener {
    fun onLoaded(nativeAd: NativeAd)
    fun onFailed()
}


internal object Native {
    private val listNative = arrayListOf<NativeAd>()
    private fun load(activity: Activity, nativeId: String, nativeListener: NativeListener) {
        val adLoader = AdLoader.Builder(activity, nativeId)
            .forNativeAd { nativeAd: NativeAd ->
                listNative.add(nativeAd)
            }
            .withAdListener(object : AdListener() {
                override fun onAdFailedToLoad(adError: LoadAdError) {
                    super.onAdFailedToLoad(adError)
                    BeeAdmob.logging("Admob Native : " + adError.message)
                    BeeAdmob.logging("Admob Native : " + adError.code)
                    nativeListener.onFailed()
                }

                override fun onAdLoaded() {
                    super.onAdLoaded()
                    nativeListener.onLoaded(listNative.random())
                }
            })
            .build()
        adLoader.loadAd(BeeAdRequest.build(activity))
    }


    fun getNative(activity: Activity, nativeId: String, nativeListener: NativeListener) {
        if (listNative.isEmpty()) {
            load(activity, nativeId, nativeListener)
        } else {
            nativeListener.onLoaded(listNative.random())
        }
    }
}