package com.example.movieapp.repository

import android.app.Application
import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import com.example.movieapp.model.Movie
import com.example.movieapp.network.api.MovieApi
import javax.inject.Inject

class MovieRepository @Inject constructor(
    private val movieApi: MovieApi,
    private val application: Application,
) {

    suspend fun getPopularMovieList(genreId: Int?): List<Movie> {
        return if (networkAvailable(application))
            movieApi.getPopularMovieList(genreId).results
        else emptyList()
    }

    suspend fun getMovieById(movieId: Long): Movie? {
        return if(networkAvailable(application))
            movieApi.getMovieById(movieId)
        else null
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