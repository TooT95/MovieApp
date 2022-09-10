package com.example.movieapp.repository

import com.example.movieapp.model.TV
import com.example.movieapp.network.api.TVApi
import javax.inject.Inject

class TVRepository @Inject constructor(private val tvApi: TVApi) {

    suspend fun getPopularTVList(): List<TV> {
        return tvApi.getPopularTVList().results
    }

}