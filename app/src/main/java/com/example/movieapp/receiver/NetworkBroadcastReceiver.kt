package com.example.movieapp.receiver

import android.content.Context
import android.net.ConnectivityManager
import android.net.ConnectivityManager.NetworkCallback
import android.net.Network
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import timber.log.Timber


class NetworkBroadcastReceiver(private val context: Context) {

    private val networkAvailableMutableLiveData = MutableLiveData<Boolean>()
    val networkAvailableLiveData: LiveData<Boolean>
        get() = networkAvailableMutableLiveData

    fun registerNetworkCallback() {
        try {
            val connectivityManager =
                context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
            connectivityManager.registerDefaultNetworkCallback(object : NetworkCallback() {
                override fun onAvailable(network: Network) {
                    networkAvailableMutableLiveData.postValue(true)
                }

                override fun onLost(network: Network) {
                    networkAvailableMutableLiveData.postValue(false)
                }
            })

        } catch (e: Exception) {
            Timber.d("CheckNetwork ${e.message}")
        }
    }
}