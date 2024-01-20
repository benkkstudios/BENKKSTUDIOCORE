package com.benkkstudio.max.types

import android.app.Activity
import android.graphics.Color
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import com.applovin.mediation.MaxAd
import com.applovin.mediation.MaxAdViewAdListener
import com.applovin.mediation.MaxError
import com.applovin.mediation.ads.MaxAdView
import com.applovin.sdk.AppLovinSdkUtils
import com.benkkstudio.max.BeeMax

internal object Banner {
    fun show(activity: Activity, view: ViewGroup, bannerId: String) {
        view.visibility = View.GONE
        val adView = MaxAdView(bannerId, activity)
        val isTablet = AppLovinSdkUtils.isTablet(activity)
        val heightPx = AppLovinSdkUtils.dpToPx(activity, if (isTablet) 90 else 50)
        adView.layoutParams = FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, heightPx)
        adView.setBackgroundColor(Color.WHITE)
        adView.setListener(object : MaxAdViewAdListener {
            override fun onAdLoaded(ad: MaxAd) {
                view.removeAllViews()
                view.addView(adView, 0)
                view.visibility = View.VISIBLE
            }

            override fun onAdDisplayed(ad: MaxAd) {
            }

            override fun onAdHidden(ad: MaxAd) {
            }

            override fun onAdClicked(ad: MaxAd) {
            }

            override fun onAdLoadFailed(adUnitId: String, error: MaxError) {
                BeeMax.logging("InterstitialAd : " + error.message)
                BeeMax.logging("InterstitialAd : " + error.code)
            }

            override fun onAdDisplayFailed(ad: MaxAd, error: MaxError) {
            }

            override fun onAdExpanded(ad: MaxAd) {
            }

            override fun onAdCollapsed(ad: MaxAd) {
            }
        })
        adView.loadAd()
    }
}