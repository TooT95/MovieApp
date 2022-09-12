package com.example.movieapp.repository

import android.app.Application
import com.example.movieapp.model.Cast
import com.example.movieapp.model.Movie
import com.example.movieapp.network.api.CastApi
import com.example.movieapp.network.api.MovieApi
import com.example.movieapp.utils.Utils
import javax.inject.Inject

class MovieRepository @Inject constructor(
    private val movieApi: MovieApi,
    private val castApi: CastApi,
    private val application: Application,
) {

    suspend fun getPopularMovieList(genreId: Int?): List<Movie> {
        return if (Utils.networkAvailable(application))
            movieApi.getPopularMovieList(genreId).results
        else emptyList()
    }

    suspend fun getMovieById(movieId: Long): Movie? {
        return if (Utils.networkAvailable(application))
            movieApi.getMovieById(movieId)
        else null
    }

    suspend fun getCastOfMovie(movieId: Long): List<Cast> {
        return if (Utils.networkAvailable(application))
            castApi.getCastListOfMovie(movieId).cast
        else emptyList()
    }
}