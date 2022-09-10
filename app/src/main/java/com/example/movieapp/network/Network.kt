package com.example.movieapp.network

import com.example.movieapp.network.api.Api
import com.example.movieapp.utils.Utils
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

object Network {

    private val client = OkHttpClient.Builder()
        .addInterceptor(ApiKeyInterceptor())
        .followRedirects(true)
        .build()

    private val retrofit = Retrofit.Builder()
        .addConverterFactory(MoshiConverterFactory.create())
        .client(client)
        .baseUrl(Utils.BASE_URl)
        .build()

    val api: Api
        get() = retrofit.create(Api::class.java)
}