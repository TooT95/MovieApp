package com.example.movieapp.repository

import com.example.movieapp.model.Movie
import com.example.movieapp.network.api.MovieApi
import javax.inject.Inject

class MovieRepository @Inject constructor(private val movieApi: MovieApi) {

    suspend fun getPopularMovieList(): List<Movie> {
        return movieApi.getPopularMovieList().results
    }

}