package com.example.movieapp.network

import com.example.movieapp.utils.Utils
import okhttp3.Interceptor
import okhttp3.Response

class ApiKeyInterceptor : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val urlBuilder = chain.request().url.newBuilder()
            .addQueryParameter("api_key", Utils.API_KEY)
            .build()
        return chain.proceed(chain.request().newBuilder().url(urlBuilder).build())
    }
}