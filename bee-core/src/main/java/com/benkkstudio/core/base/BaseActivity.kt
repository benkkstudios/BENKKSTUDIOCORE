package com.benkkstudio.core.base

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.viewbinding.ViewBinding
import com.benkkstudio.core.ext.checkInternetConnectivity
import com.benkkstudio.core.ext.getBinding
import com.benkkstudio.core.interfaces.NetworkInterface
import com.benkkstudio.core.networking.ApiStatus
import com.benkkstudio.core.viewmodel.GlobalViewModel
import com.benkkstudio.core.viewmodel.ViewModelFactory
import com.benkkstudio.core.widget.DialogLoading

@Suppress("MemberVisibilityCanBePrivate")
abstract class BaseActivity<V : ViewBinding> : AppCompatActivity() {
    lateinit var binding: V
    lateinit var viewModel: GlobalViewModel
    lateinit var loading: DialogLoading
    override fun onCreate(savedInstanceState: Bundle?) {
        beforeOnCreate()
        super.onCreate(savedInstanceState)
        binding = getBinding()
        setupViewModel()
        setContentView(binding.root)
        loading = DialogLoading(this)
    }

    private fun setupViewModel() {
        viewModel = ViewModelProvider(this, ViewModelFactory())[GlobalViewModel::class.java]
    }

    open fun beforeOnCreate() {}

    inline fun <reified T> get(
        api: String, networkInterface: NetworkInterface<T>? = null, params: HashMap<String, String>? = null,
        header: HashMap<String, String>? =
            null,
    ) {
        if (checkInternetConnectivity()) {
            viewModel.get<T>(api, params, header).observe(this) { result ->
                when (result.status) {
                    ApiStatus.LOADING -> {
                        loading.show()
                    }

                    ApiStatus.ERROR -> {
                        loading.dismiss()
                        networkInterface?.error()
                    }

                    ApiStatus.SUCCESS -> {
                        result.data?.data?.let {
                            networkInterface?.success(it)
                            loading.dismiss()
                        }
                    }
                }
            }
        } else {
            loading.dismiss()
            networkInterface?.connectionError()
        }
    }

    inline fun <reified T> post(
        api: String, networkInterface: NetworkInterface<T>? = null, params: HashMap<String, String>? = null,
        header: HashMap<String, String>? =
            null,
    ) {
        if (checkInternetConnectivity()) {
            viewModel.post<T>(api, params, header).observe(this) { result ->
                when (result.status) {
                    ApiStatus.LOADING -> {
                        loading.show()
                    }

                    ApiStatus.ERROR -> {
                        loading.dismiss()
                        networkInterface?.error()
                    }

                    ApiStatus.SUCCESS -> {
                        result.data?.data?.let {
                            networkInterface?.success(it)
                            loading.dismiss()
                        }
                    }
                }
            }
        } else {
            loading.dismiss()
            networkInterface?.connectionError()
        }
    }
}
