package com.example.movieapp.repository

import android.app.Application
import com.example.movieapp.model.Genre
import com.example.movieapp.network.api.GenreApi
import com.example.movieapp.utils.Utils
import javax.inject.Inject

class GenreRepository @Inject constructor(
    private val genreApi: GenreApi,
    private val application: Application,
) {

    suspend fun getGenreMovieList(): List<Genre> {
        return if (Utils.networkAvailable(application))
            genreApi.getGenreMovieList().genres
        else emptyList()
    }

    suspend fun getGenreTVList(): List<Genre> {
        return if (Utils.networkAvailable(application))
            genreApi.getGenreTVList().genres
        else emptyList()
    }
}