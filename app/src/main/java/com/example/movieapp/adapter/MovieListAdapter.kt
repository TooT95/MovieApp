package com.example.movieapp.adapter

import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.movieapp.R
import com.example.movieapp.databinding.ItemListMovieTvBinding
import com.example.movieapp.extensions.getPathWithBaseUrl
import com.example.movieapp.extensions.glideImage
import com.example.movieapp.extensions.inflateLayout
import com.example.movieapp.model.Movie

class MovieListAdapter(private val onItemClicked: (itemId: Int) -> Unit) :
    ListAdapter<Movie, MovieListAdapter.MovieListHolder>(MovieDiffUtil()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieListHolder {
        return MovieListHolder(onItemClicked, parent.inflateLayout(R.layout.item_list_movie_tv))
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

        private val binding = ItemListMovieTvBinding.bind(view)
        fun onBind(movie: Movie) {
            binding.txtMovieName.text = movie.title
            binding.ivMovieIcon.glideImage(itemView, movie.poster_path.getPathWithBaseUrl())
            binding.txtRate.text = movie.vote_average.toString()
        }

    }

    class MovieDiffUtil : DiffUtil.ItemCallback<Movie>() {
        override fun areItemsTheSame(oldItem: Movie, newItem: Movie) = (oldItem.id == newItem.id)

        override fun areContentsTheSame(oldItem: Movie, newItem: Movie) = (oldItem == newItem)

    }
}