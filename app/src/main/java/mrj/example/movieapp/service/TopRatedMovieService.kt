package mrj.example.movieapp.service

import mrj.example.movieapp.model.GenreList
import mrj.example.movieapp.model.TopRatedMovie
import retrofit2.Call
import retrofit2.http.GET


/**
* Created by JavohirAI
*/

interface TopRatedMovieService {
    @GET("movie/top_rated?api_key=17abbcf1430dc572182b2a658146188d")
    fun getTopRatedMovies(): Call<TopRatedMovie>
}