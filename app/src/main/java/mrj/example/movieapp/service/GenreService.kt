package mrj.example.movieapp.service

import mrj.example.movieapp.model.Genre
import mrj.example.movieapp.model.GenreList
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query


/**
 * Created by JavohirAI
 */

interface GenreService {
    @GET("genre/movie/list?api_key=17abbcf1430dc572182b2a658146188d")
    fun getGenreList(): Call<GenreList>
}