package mrj.example.movieapp.model


/**
 * Created by JavohirAI
 */

data class Genre(var id: Int = 0, var name: String = "")

data class GenreList(var genres: MutableList<Genre>)