package com.example.movieapp.adapter

import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.movieapp.R
import com.example.movieapp.databinding.ItemListMovieBinding
import com.example.movieapp.extensions.getPathWithBaseUrl
import com.example.movieapp.extensions.glideImage
import com.example.movieapp.extensions.inflateLayout
import com.example.movieapp.model.Movie

class MovieListAdapter(private val onItemClicked: (itemId: Int) -> Unit) :
    RecyclerView.Adapter<MovieListAdapter.MovieListHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieListHolder {
        return MovieListHolder(onItemClicked, parent.inflateLayout(R.layout.item_list_movie))
    }

    override fun onBindViewHolder(holder: MovieListHolder, position: Int) {
        holder.onBind(movieList[position])
    }

    override fun getItemCount() = movieList.size

    var movieList = emptyList<Movie>()

    class MovieListHolder(private val onItemClicked: (itemId: Int) -> Unit, view: View) :
        RecyclerView.ViewHolder(view) {

        init {
            itemView.setOnClickListener {
                onItemClicked(adapterPosition)
            }
        }

        private val binding = ItemListMovieBinding.bind(view)
        fun onBind(movie: Movie) {
            binding.txtMovieName.text = movie.title
            binding.ivMovieIcon.glideImage(itemView, movie.poster_path.getPathWithBaseUrl())
            binding.txtRate.text = movie.vote_average.toString()
        }

    }

}