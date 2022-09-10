package com.example.movieapp.repository

import com.example.movieapp.model.Genre
import com.example.movieapp.network.GenreWrapper
import com.example.movieapp.network.Network

class GenreRepository {

    suspend fun getGenreList(): List<Genre> {
        return Network.api.getGenreList().genres
    }

}