package com.example.movieapp.repository

import com.example.movieapp.model.Genre
import com.example.movieapp.network.api.GenreApi
import javax.inject.Inject

class GenreRepository @Inject constructor(private val genreApi: GenreApi) {

    suspend fun getGenreMovieList(): List<Genre> {
        return genreApi.getGenreMovieList().genres
    }

    suspend fun getGenreTVList(): List<Genre> {
        return genreApi.getGenreTVList().genres
    }

}