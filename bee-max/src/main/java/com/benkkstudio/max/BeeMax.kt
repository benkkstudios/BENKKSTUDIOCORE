package com.benkkstudio.max

import android.app.Activity
import android.util.Log
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import androidx.annotation.LayoutRes
import com.applovin.mediation.nativeAds.MaxNativeAdView
import com.applovin.sdk.AppLovinPrivacySettings
import com.applovin.sdk.AppLovinSdk
import com.applovin.sdk.AppLovinSdkConfiguration
import com.benkkstudio.consent.BeeConsent
import com.benkkstudio.consent.BeeConsentCallback
import com.benkkstudio.max.model.MaxModel
import com.benkkstudio.max.types.Banner
import com.benkkstudio.max.types.Interstitial
import com.benkkstudio.max.types.Native
import com.benkkstudio.max.types.NativeListener
import com.benkkstudio.max.types.Reward
import com.benkkstudio.max.widget.DialogLoading

class BeeMax {
    companion object {
        private lateinit var maxModel: MaxModel
        private lateinit var dialogLoading: DialogLoading

        internal fun build(activity: Activity, maxModel: MaxModel, loadingTime: Long) {
            this.maxModel = maxModel
            dialogLoading = DialogLoading(activity, loadingTime, maxModel.customLadingView)
            requestConsent(activity)
        }

        internal fun logging(any: Any) {
            try {
                if (maxModel.enableLogging) {
                    Log.e("ABENK : ", any.toString())
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }

        private fun requestConsent(activity: Activity) {
            BeeConsent.Builder(activity)
                .debugMode(maxModel.debugMode)
                .enableLogging(maxModel.enableLogging)
                .listener(object : BeeConsentCallback {
                    override fun onRequested() {
                        initMax(activity)
                    }
                })
                .request()
        }

        private fun initMax(activity: Activity) {
            AppLovinSdk.getInstance(activity).mediationProvider = "max"
            AppLovinPrivacySettings.setHasUserConsent(BeeConsent.isConsent(activity), activity)
            AppLovinSdk.getInstance(activity).initializeSdk {
                loadInterstitial(activity)
                loadReward(activity)
            }
        }

        private fun loadInterstitial(activity: Activity) {
            if (maxModel.interstitialId.isNotEmpty()) {
                Interstitial.load(activity, maxModel.interstitialId)
            }
        }

        fun showInterstitial(activity: Activity, callback: (() -> Unit)? = null) {
            if (maxModel.interstitialId.isNotEmpty()) {
                dialogLoading.showAndDismiss {
                    Interstitial.show(activity, maxModel.interstitialId, callback)
                }
            } else callback?.invoke()
        }

        private var clickCount = 0
        fun showInterstitialRandom(activity: Activity, callback: (() -> Unit)? = null) {
            maxModel.interstitialInterval?.let { interval ->
                clickCount++
                if (clickCount == interval) {
                    showInterstitial(activity, callback)
                    clickCount = 0
                } else callback?.invoke()
            }
        }

        private fun loadReward(activity: Activity) {
            if (maxModel.rewardId.isNotEmpty()) {
                Reward.load(activity, maxModel.rewardId)
            }
        }

        fun showReward(activity: Activity, callback: (() -> Unit)? = null) {
            if (maxModel.rewardId.isNotEmpty()) {
                dialogLoading.showAndDismiss {
                    Reward.show(activity, maxModel.rewardId, callback)
                }
            } else callback?.invoke()
        }

        fun loadNative(activity: Activity, nativeAdContainer: FrameLayout) {
            nativeAdContainer.visibility = View.GONE
            if (maxModel.nativeId.isNotEmpty()) {
                Native.getNative(activity, maxModel.nativeId, object : NativeListener {
                    override fun onLoaded(nativeAd: MaxNativeAdView) {
                        nativeAdContainer.removeAllViews()
                        nativeAdContainer.addView(nativeAd)
                        nativeAdContainer.visibility = View.VISIBLE
                    }

                    override fun onFailed() {
                        nativeAdContainer.visibility = View.GONE
                    }

                })
            }
        }

        fun showBanner(activity: Activity, adsContainer: ViewGroup) {
            if (maxModel.bannerId.isNotEmpty()) {
                Banner.show(activity, adsContainer, maxModel.bannerId)
            }
        }
    }

    class Builder(private val activity: Activity) {
        private val maxModel = MaxModel()
        private var loadingTime = 0L
        fun enableLogging(enableLogging: Boolean) = apply { maxModel.enableLogging = enableLogging }
        fun debugMode(debugMode: Boolean) = apply { maxModel.debugMode = debugMode }
        fun interstitialId(interstitialId: String) = apply { maxModel.interstitialId = interstitialId }
        fun interstitialInterval(interstitialInterval: Int) = apply { maxModel.interstitialInterval = interstitialInterval }
        fun bannerId(bannerId: String) = apply { maxModel.bannerId = bannerId }
        fun rewardId(rewardId: String) = apply { maxModel.rewardId = rewardId }
        fun nativeId(nativeId: String) = apply { maxModel.nativeId = nativeId }
        fun withLoading(withLoading: Boolean, loadingTime: Long) = apply {
            maxModel.withLoading = withLoading
            this.loadingTime = loadingTime
        }

        fun customLadingView(@LayoutRes customLadingView: Int) = apply { maxModel.customLadingView = customLadingView }
        fun request() = build(activity, maxModel, loadingTime)
    }
}