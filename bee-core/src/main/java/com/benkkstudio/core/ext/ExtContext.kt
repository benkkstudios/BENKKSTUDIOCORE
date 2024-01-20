package com.benkkstudio.core.ext

import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import android.net.Network
import android.net.NetworkCapabilities
import android.net.NetworkRequest
import android.os.Bundle

fun Context.checkInternetConnectivity(): Boolean {
    val connectivityManager: ConnectivityManager = getSystemService(ConnectivityManager::class.java) as ConnectivityManager
    var currentNetwork = connectivityManager.activeNetwork
    var currentNetworkCapabilities = connectivityManager.getNetworkCapabilities(currentNetwork)

    val networkRequest = NetworkRequest.Builder()
        .addCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET)
        .addTransportType(NetworkCapabilities.TRANSPORT_WIFI)
        .addTransportType(NetworkCapabilities.TRANSPORT_CELLULAR)
        .build()

    val networkCallback = object : ConnectivityManager.NetworkCallback() {
        override fun onCapabilitiesChanged(
            network: Network,
            networkCapabilities: NetworkCapabilities
        ) {
            super.onCapabilitiesChanged(network, networkCapabilities)
            currentNetwork = network
            currentNetworkCapabilities = networkCapabilities
        }
    }

    connectivityManager.registerNetworkCallback(networkRequest, networkCallback)

    return when {
        currentNetworkCapabilities?.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) == true -> true
        currentNetworkCapabilities?.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) == true -> true
        else -> false
    }
}


fun Context.startAct(clazz: Class<*>) {
    startActivity(Intent(this, clazz))
}

fun Context.startAct(clazz: Class<*>, bundle: Bundle) {
    Intent(this, clazz).apply {
        putExtras(bundle)
        startActivity(this)
    }
}
