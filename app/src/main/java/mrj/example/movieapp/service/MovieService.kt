package mrj.example.movieapp.service

import mrj.example.movieapp.model.GenreList
import retrofit2.Call
import retrofit2.http.GET


/**
 * Created by JavohirAI
 */

interface MovieService {
    @GET("genre/movie/list?api_key=17abbcf1430dc572182b2a658146188d")
    fun getGenreList(): Call<GenreList>
}