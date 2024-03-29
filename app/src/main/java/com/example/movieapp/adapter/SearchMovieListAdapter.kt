package com.example.movieapp.adapter

import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.movieapp.R
import com.example.movieapp.databinding.ItemSearchBinding
import com.example.movieapp.extensions.getPathWithBaseUrl
import com.example.movieapp.extensions.glideImage
import com.example.movieapp.extensions.inflateLayout
import com.example.movieapp.model.Movie

class SearchMovieListAdapter(private val onItemClicked: (itemId: Int) -> Unit) :
    ListAdapter<Movie, SearchMovieListAdapter.MovieListHolder>(ObjectDiffUtil<Movie>()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieListHolder {
        return MovieListHolder(onItemClicked, parent.inflateLayout(R.layout.item_search))
    }

    override fun onBindViewHolder(holder: MovieListHolder, position: Int) {
        holder.onBind(currentList[position])
    }

    override fun getItemCount() = currentList.size

    class MovieListHolder(private val onItemClicked: (itemId: Int) -> Unit, view: View) :
        RecyclerView.ViewHolder(view) {

        init {
            itemView.setOnClickListener {
                onItemClicked(adapterPosition)
            }
        }

        private val binding = ItemSearchBinding.bind(view)
        fun onBind(movie: Movie) {
            with(binding) {
                txtMovieName.text = movie.title
                ivMovieIcon.glideImage(itemView,
                    movie.poster_path?.getPathWithBaseUrl() ?: "")
                txtRate.text = movie.vote_average.toString()
            }
        }

    }
}