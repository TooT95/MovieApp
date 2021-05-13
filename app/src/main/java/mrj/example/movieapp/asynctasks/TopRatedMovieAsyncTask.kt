package mrj.example.movieapp.asynctasks

import android.app.Activity
import android.os.AsyncTask
import mrj.example.movieapp.activity.MainActivity
import mrj.example.movieapp.common.GenreCommon
import mrj.example.movieapp.common.TopRatedMovieCommon
import mrj.example.movieapp.model.GenreList
import mrj.example.movieapp.model.TopRatedMovie
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


/**
 * Created by JavohirAI
 */

class TopRatedMovieAsyncTask(var activity: Activity) : AsyncTask<Unit, Unit, Unit>() {

    override fun doInBackground(vararg params: Unit?) {

        var genreService = TopRatedMovieCommon.retrofitService
        genreService.getTopRatedMovies()
            .enqueue(object : Callback<TopRatedMovie> {
                override fun onFailure(call: Call<TopRatedMovie>, t: Throwable) {

                }

                override fun onResponse(
                    call: Call<TopRatedMovie>,
                    response: Response<TopRatedMovie>
                ) {
                    if (response.body() != null) {
                        var adapter = (response.body() as TopRatedMovie).results
                        (this@TopRatedMovieAsyncTask.activity as MainActivity).printTopRatedMovie(
                            adapter
                        )
                    }

                }

            })

    }
}