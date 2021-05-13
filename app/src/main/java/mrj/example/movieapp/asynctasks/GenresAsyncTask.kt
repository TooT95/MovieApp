package mrj.example.movieapp.asynctasks

import android.app.Activity
import android.os.AsyncTask
import com.google.android.material.tabs.TabLayout
import mrj.example.movieapp.activity.MainActivity
import mrj.example.movieapp.R
import mrj.example.movieapp.common.GenreCommon
import mrj.example.movieapp.model.Genre
import mrj.example.movieapp.model.GenreList
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


/**
 * Created by JavohirAI
 */

class GenresAsyncTask(var context: Activity) : AsyncTask<String, Unit, Unit>() {

    lateinit var adapter: MutableList<Genre>

    override fun doInBackground(vararg params: String?) {
        adapter = mutableListOf()
        var genreService = GenreCommon.retrofitService
        genreService.getGenreList()
            .enqueue(object : Callback<GenreList> {
                override fun onFailure(call: Call<GenreList>, t: Throwable) {

                }

                override fun onResponse(
                    call: Call<GenreList>,
                    response: Response<GenreList>
                ) {
                    if (response.body() != null) {
                        adapter = (response.body() as GenreList).genres
                    }
                    this@GenresAsyncTask.printTabs()
                }

            })
    }

    private fun printTabs() {
        context as MainActivity
        val tabLayout =
            context.findViewById<TabLayout>(R.id.tabLayout)

        tabLayout.setTabGravity(TabLayout.GRAVITY_CENTER);
        tabLayout.setTabMode(TabLayout.MODE_SCROLLABLE);

        adapter.forEach {
            val tab = tabLayout.newTab()
            tab.text = it.name
            tabLayout.addTab(tab)
        }
    }

}