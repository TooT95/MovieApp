package com.example.movieapp.repository

import android.app.Application
import com.example.movieapp.model.Cast
import com.example.movieapp.model.TV
import com.example.movieapp.network.api.CastApi
import com.example.movieapp.network.api.TVApi
import com.example.movieapp.utils.Utils
import javax.inject.Inject

class TVRepository @Inject constructor(
    private val tvApi: TVApi,
    private val castApi: CastApi,
    private val application: Application,
) {

    suspend fun getPopularTVList(genreId: Int?): List<TV> {
        return tvApi.getPopularTVList(genreId).results
    }

    suspend fun getTVById(tvId: Long): TV? {
        return if (Utils.networkAvailable(application))
            tvApi.getTvById(tvId)
        else null
    }

    suspend fun getCastOfTv(tvId: Long): List<Cast> {
        return if (Utils.networkAvailable(application))
            castApi.getCastListOfTV(tvId).cast
        else emptyList()
    }

    suspend fun getTvByQueryText(queryText: String): List<TV> {
        return if (Utils.networkAvailable(application))
            tvApi.getTvByQueryText(queryText).results
        else emptyList()
    }
}