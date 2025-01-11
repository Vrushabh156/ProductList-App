package com.appsvit.productlist_app.utils

import android.content.Context
import android.net.ConnectivityManager
import android.net.Network
import android.net.NetworkCapabilities
import android.net.NetworkRequest
import androidx.lifecycle.LiveData
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject
import javax.inject.Singleton

/*
    This network util class will monitor network state changes and based on callback function,
    we will post the value to the ui layer
 */

@Singleton
class NetworkUtil @Inject constructor(
    @ApplicationContext private val context: Context
) : LiveData<Status>() {

    private val connectivityManager =
        context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

    // Define NetworkRequest once for efficiency
    private val networkRequest = NetworkRequest.Builder()
        .addCapability(NetworkCapabilities.NET_CAPABILITY_VALIDATED)
        .addCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET)
        .addTransportType(NetworkCapabilities.TRANSPORT_WIFI)
        .addTransportType(NetworkCapabilities.TRANSPORT_CELLULAR)
        .addTransportType(NetworkCapabilities.TRANSPORT_ETHERNET)
        .build()

    // Define NetworkCallback
    private val networkCallback = object : ConnectivityManager.NetworkCallback() {
        override fun onAvailable(network: Network) {
            postValue(Status.AVAILABLE)
        }

        override fun onLosing(network: Network, maxMsToLive: Int) {
            postValue(Status.LOSING)
        }

        override fun onLost(network: Network) {
            postValue(Status.UNAVAILABLE)
        }
    }

    override fun onActive() {
        super.onActive()
        // Check initial connectivity status
        checkInitialNetworkStatus()
        // Register the callback
        connectivityManager.registerNetworkCallback(networkRequest, networkCallback)
    }

    override fun onInactive() {
        super.onInactive()
        // Unregister the callback to prevent memory leaks
        connectivityManager.unregisterNetworkCallback(networkCallback)
    }

    // Function to check the current network status synchronously
    private fun checkInitialNetworkStatus() {
        val activeNetwork = connectivityManager.activeNetwork
        val networkCapabilities = connectivityManager.getNetworkCapabilities(activeNetwork)

        if (networkCapabilities != null &&
            networkCapabilities.hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET)
        ) {
            postValue(Status.AVAILABLE)
        } else {
            postValue(Status.UNAVAILABLE)
        }
    }
}
