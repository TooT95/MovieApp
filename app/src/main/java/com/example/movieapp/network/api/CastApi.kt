package com.example.movieapp.network.api

import com.example.movieapp.model.Cast
import com.example.movieapp.network.CastWrapper
import retrofit2.http.GET
import retrofit2.http.Path

interface CastApi {

    @GET("movie/{movieId}/credits")
    suspend fun getCastListOfMovie(@Path("movieId") movieId: Long): CastWrapper<Cast>

    @GET("tv/{tvId}/credits")
    suspend fun getCastListOfTV(@Path("tvId") tvId: Long): CastWrapper<Cast>

}