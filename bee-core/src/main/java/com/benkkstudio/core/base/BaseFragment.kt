package com.benkkstudio.core.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
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
abstract class BaseFragment<V : ViewBinding> : Fragment() {
    private var _binding: V? = null
    val binding: V
        get() = _binding
            ?: throw RuntimeException("Should only use binding after onCreateView and before onDestroyView")
    private var _view: View? = null
    lateinit var viewModel: GlobalViewModel

    lateinit var loading: DialogLoading
    open fun onStarted(savedInstanceState: Bundle?) {}

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        setupViewModel()
        loading = DialogLoading(requireContext())
        _binding = getBinding(inflater, container)
        onStarted(savedInstanceState)
        _view = binding.root
        return binding.root
    }

    private fun setupViewModel() {
        viewModel = ViewModelProvider(this, ViewModelFactory())[GlobalViewModel::class.java]
    }

    inline fun <reified T> get(
        api: String, networkInterface: NetworkInterface<T>, params: HashMap<String, String>? = null,
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
                        networkInterface.error()
                    }

                    ApiStatus.SUCCESS -> {
                        loading.dismiss()
                        result.data?.data?.let {
                            networkInterface.success(it)
                        }
                    }
                }
            }
        } else {
            loading.dismiss()
            networkInterface.connectionError()
        }
    }

    inline fun <reified T> post(
        api: String, networkInterface: NetworkInterface<T>, params: HashMap<String, String>? = null,
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
                        networkInterface.error()
                    }

                    ApiStatus.SUCCESS -> {
                        loading.dismiss()
                        result.data?.data?.let {
                            networkInterface.success(it)
                        }
                    }
                }
            }
        } else {
            loading.dismiss()
            networkInterface.connectionError()
        }
    }
}
