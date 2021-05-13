package mrj.example.movieapp.common

import mrj.example.movieapp.client.Client
import mrj.example.movieapp.service.GenreService
import mrj.example.movieapp.service.TopRatedMovieService


/**
 * Created by JavohirAI
 */

object TopRatedMovieCommon {
    private val BASE_URL = "https://api.themoviedb.org/3/"
    val retrofitService: TopRatedMovieService
        get() = Client.getClient(BASE_URL).create(TopRatedMovieService::class.java)
}