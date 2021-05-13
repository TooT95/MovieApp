package mrj.example.movieapp.model


/**
 * Created by JavohirAI
 */

data class Movie(
    var backdrop_path: String = "",
    var id: Int = 0,
    var original_language: String = "",
    var original_title: String = "",
    var overview: String = "",
    var poster_path: String = "",
    var title: String = "",
    var vote_average: Double = 0.0
)

data class TopRatedMovie(var page: Int = 0, var results: MutableList<Movie>)