package com.example.movieapp.repository

import com.example.movieapp.model.Genre
import com.example.movieapp.network.api.GenreApi
import javax.inject.Inject

class GenreRepository @Inject constructor(private val genreApi: GenreApi) {

    suspend fun getGenreList(): List<Genre> {
        return genreApi.getGenreList().genres
    }

}