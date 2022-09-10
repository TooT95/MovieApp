package com.example.movieapp.network.api

import com.example.movieapp.network.GenreWrapper
import retrofit2.http.GET

interface Api {

    @GET("genre/movie/list")
    suspend fun getGenreList():GenreWrapper

}