package com.example.movieapp.network.api

import com.example.movieapp.model.TV
import com.example.movieapp.network.UniversalWrapper
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface TVApi {

    @GET("discover/tv")
    suspend fun getPopularTVList(@Query("with_genres") genreId: Int? = null): UniversalWrapper<TV>

    @GET("tv/{tvId}")
    suspend fun getTvById(@Path("tvId") movieId: Long): TV?
}