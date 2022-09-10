package com.example.movieapp.network

import com.example.movieapp.network.api.GenreApi
import com.example.movieapp.network.api.MovieApi
import com.example.movieapp.network.api.TVApi
import com.example.movieapp.utils.Utils
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

@Module
@InstallIn(SingletonComponent::class)
class NetworkModule {

    @Provides
    fun provideClient(): OkHttpClient {
        return OkHttpClient.Builder()
            .addInterceptor(ApiKeyInterceptor())
            .followRedirects(true)
            .build()
    }

    @Provides
    fun provideRetrofit(client: OkHttpClient): Retrofit {
        return Retrofit.Builder()
            .addConverterFactory(MoshiConverterFactory.create())
            .client(client)
            .baseUrl(Utils.BASE_URl)
            .build()
    }

    @Provides
    fun provideGenreApi(retrofit: Retrofit): GenreApi {
        return retrofit.create(GenreApi::class.java)
    }

    @Provides
    fun provideMovieApi(retrofit: Retrofit): MovieApi {
        return retrofit.create(MovieApi::class.java)
    }

    @Provides
    fun provideTVApi(retrofit: Retrofit): TVApi {
        return retrofit.create(TVApi::class.java)
    }

}