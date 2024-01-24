package com.benkkstudio.coresample

import android.os.Bundle
import androidx.lifecycle.ViewModel
import com.benkkstudio.admob.BeeAdmob
import com.benkkstudio.core.appInstance
import com.benkkstudio.core.base.BaseActivity
import com.benkkstudio.core.ext.click
import com.benkkstudio.core.interfaces.NetworkInterface
import com.benkkstudio.core.logging
import com.benkkstudio.coresample.databinding.ActivityMainBinding
import com.benkkstudio.max.BeeMax

class MainActivity : BaseActivity<ActivityMainBinding>() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        getConfig()

        //   initAdmob()
        initAdmob()
    }

    private fun initMax() {
        BeeMax.Builder(this)
            .bannerId("6b70bd5d17c8db7d")
            .interstitialId("1f1a08fd4a81dda9")
            .rewardId("1f1a08fd4a81dda9")
            .enableLogging(true)
            .debugMode(true)
            .request()
        BeeMax.showBanner(this, binding.bannerContainer)
        binding.buttonInter.click {
            BeeMax.showInterstitial(this) {
                logging("inter closed")
            }
        }

        binding.buttonReward.click {
            BeeMax.showReward(this) {
                logging("reward closed")
            }
        }
    }


    private fun initAdmob() {
        BeeAdmob.Builder(this)
            .bannerId(BeeAdmob.DummyAdmob.BANNER)
            .interstitialId(BeeAdmob.DummyAdmob.INTERSTITIAL)
            .rewardId(BeeAdmob.DummyAdmob.REWARD)
            .nativeId(BeeAdmob.DummyAdmob.NATIVE)
            .withOpenAd(appInstance, BeeAdmob.DummyAdmob.OPEN)
            .withLoading(true, 1000)
            .enableLogging(true)
            .debugMode(true)
            .request()
        BeeAdmob.loadNative(this, binding.nativeView)
        BeeAdmob.showBanner(this, binding.bannerContainer)
        binding.buttonInter.click {
            BeeAdmob.showInterstitial(this) {
                logging("inter closed")
            }
        }

        binding.buttonReward.click {
            BeeAdmob.showReward(this) {
                logging("reward closed")
            }
        }
    }

    private fun getConfig() {
        get(api = "config2.json", networkInterface = object : NetworkInterface<ModelAds> {
            override fun success(data: ModelAds) {
                logging(data.ADMOB_BANNER)
                logging(data.ADMOB_INTER)
                logging("success")
                getConfig2()
            }

            override fun error() {
                logging("error")
            }

            override fun connectionError() {
                logging("connectionError")
            }
        })
    }

    private fun getConfig2() {
        get(api = "config2.json", networkInterface = object : NetworkInterface<ModelAds> {
            override fun success(data: ModelAds) {
                logging(data.ADMOB_BANNER)
                logging(data.ADMOB_INTER)
                logging("success")
            }

            override fun error() {
                logging("error")
            }

            override fun connectionError() {
                logging("connectionError")
            }
        })
    }
}