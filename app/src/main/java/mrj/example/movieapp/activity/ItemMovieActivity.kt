package mrj.example.movieapp.activity

import android.os.Bundle
import android.widget.Toast
import mrj.example.movieapp.R


/**
 * Created by JavohirAI
 */

class ItemMovieActivity :
    BaseActivity(
        layoutResourceId = R.layout.item_movie,
        homeDislpayEnabled = true,
        titleId = R.string.empty_title
    ) {

    var currentMovieID = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        if (intent.extras != null) {
            currentMovieID = intent.extras!!.getInt(MainActivity.MOVIE_ID)


        }

    }
}