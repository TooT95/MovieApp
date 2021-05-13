package mrj.example.movieapp.adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import mrj.example.movieapp.R
import mrj.example.movieapp.activity.ItemMovieActivity
import mrj.example.movieapp.activity.MainActivity
import mrj.example.movieapp.model.Movie

/**
 * Created by JavohirAI
 */

class PagerAdapter(private val context: Context, private val movies: List<Movie>) :
    RecyclerView.Adapter<PagerAdapter.PageHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PageHolder =
        PageHolder(LayoutInflater.from(context).inflate(R.layout.pager_item, parent, false))


    override fun onBindViewHolder(holder: PageHolder, position: Int) {
        Picasso.with(context)
            .load("https://image.tmdb.org/t/p/w500${movies[position].poster_path}")
            .into(holder.imageView);
        holder.title.text = movies[position].title
        holder.rating.text = movies[position].vote_average.toString()
        holder.linear.setOnClickListener {
            val intent = Intent(context, ItemMovieActivity::class.java)
            intent.putExtra(MainActivity.MOVIE_ID, movies[position].id)
            context.startActivity(intent)
        }
    }

    override fun getItemCount(): Int = movies.size

    inner class PageHolder(view: View) : RecyclerView.ViewHolder(view) {
        val imageView: ImageView = view.findViewById(R.id.imageView)
        val title: TextView = view.findViewById(R.id.name_movie)
        val rating: TextView = view.findViewById(R.id.rate_video)
        val linear: LinearLayout = view.findViewById(R.id.linear_pager)
    }
}