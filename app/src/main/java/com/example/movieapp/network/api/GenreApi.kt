package com.example.movieapp.network.api

import com.example.movieapp.network.GenreWrapper
import retrofit2.http.GET

interface GenreApi {

    @GET("genre/movie/list")
    suspend fun getGenreList():GenreWrapper

}