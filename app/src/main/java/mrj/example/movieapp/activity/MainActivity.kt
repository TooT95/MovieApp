package mrj.example.movieapp.activity

import android.content.Intent
import android.os.Bundle
import android.widget.LinearLayout
import androidx.viewpager2.widget.ViewPager2
import mrj.example.movieapp.adapter.PagerAdapter
import mrj.example.movieapp.R
import mrj.example.movieapp.asynctasks.GenresAsyncTask
import mrj.example.movieapp.asynctasks.TopRatedMovieAsyncTask
import mrj.example.movieapp.model.Movie

class MainActivity : BaseActivity(layoutResourceId = R.layout.activity_main) {

    var currentMovieId = 0

    companion object {
        const val MOVIE_ID = "mrj.example.movieapp.activity.movie_id"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        GenresAsyncTask(this).execute()
        TopRatedMovieAsyncTask(this).execute()
    }

    fun printTopRatedMovie(movies: MutableList<Movie>) {
        findViewById<ViewPager2>(R.id.pager).apply {
            adapter =
                PagerAdapter(
                    this@MainActivity, movies
                )
        }
    }

}