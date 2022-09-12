package com.example.movieapp.repository

import android.app.Application
import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import com.example.movieapp.model.Genre
import com.example.movieapp.network.api.GenreApi
import javax.inject.Inject

class GenreRepository @Inject constructor(
    private val genreApi: GenreApi,
    private val application: Application,
) {

    suspend fun getGenreMovieList(): List<Genre> {
        return if (networkAvailable(application))
            genreApi.getGenreMovieList().genres
        else emptyList()
    }

    suspend fun getGenreTVList(): List<Genre> {
        return if (networkAvailable(application))
            genreApi.getGenreTVList().genres
        else emptyList()
    }

    private fun networkAvailable(context: Context): Boolean {
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