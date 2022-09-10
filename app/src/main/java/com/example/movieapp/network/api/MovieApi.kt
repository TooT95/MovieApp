package com.example.movieapp.network.api

import com.example.movieapp.model.Movie
import com.example.movieapp.network.UniversalWrapper
import retrofit2.http.GET
import retrofit2.http.Path

interface MovieApi {

    @GET("discover/movie")
    suspend fun getPopularMovieList(): UniversalWrapper<Movie>

}