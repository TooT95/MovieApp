package mrj.example.movieapp.common

import mrj.example.movieapp.client.Client
import mrj.example.movieapp.service.GenreService


/**
 * Created by JavohirAI
 */

object GenreCommon {
    private val BASE_URL = "https://api.themoviedb.org/3/"
    val retrofitService: GenreService
        get() = Client.getClient(BASE_URL).create(GenreService::class.java)
}