package com.example.movieapp.network.api

import com.example.movieapp.model.TV
import com.example.movieapp.network.UniversalWrapper
import retrofit2.http.GET

interface TVApi {

    @GET("discover/tv")
    suspend fun getPopularTVList(): UniversalWrapper<TV>

}