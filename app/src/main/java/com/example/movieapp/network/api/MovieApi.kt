package com.example.movieapp.network.api

import com.example.movieapp.model.Movie
import com.example.movieapp.network.UniversalWrapper
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface MovieApi {

    @GET("discover/movie")
    suspend fun getPopularMovieList(@Query("with_genres") genreId: Int? = null): UniversalWrapper<Movie>

    @GET("movie/{movieId}")
    suspend fun getMovieById(@Path("movieId") movieId: Long): Movie

    @GET("search/movie")
    suspend fun getMovieByQueryText(@Query("query") queryText: String): UniversalWrapper<Movie>
}