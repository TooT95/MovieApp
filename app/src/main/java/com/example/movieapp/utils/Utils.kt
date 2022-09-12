package com.example.movieapp.utils

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities

object Utils {

    const val BASE_IMAGE_URL = "https://image.tmdb.org/t/p/w500"
    const val API_KEY = "17abbcf1430dc572182b2a658146188d"
    const val BASE_URl = "https://api.themoviedb.org/3/"

    fun networkAvailable(context: Context): Boolean {
        val connectivityManager =
            context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val networkCapabilities = connectivityManager.activeNetwork ?: return false
        val actNw =
            connectivityManager.getNetworkCapabilities(networkCapabilities) ?: return false
        return when {
            actNw.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> true
            actNw.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> true
            actNw.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET) -> true
            else -> false
        }
    }
}