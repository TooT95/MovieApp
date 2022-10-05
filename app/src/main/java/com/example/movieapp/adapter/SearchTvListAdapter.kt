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
import com.example.movieapp.model.TV

class SearchTvListAdapter(private val onItemClicked: (itemId: Int) -> Unit) :
    ListAdapter<TV, SearchTvListAdapter.MovieListHolder>(ObjectDiffUtil<TV>()) {

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
        fun onBind(tv: TV) {
            binding.txtMovieName.text = tv.name
            binding.ivMovieIcon.glideImage(itemView, tv.poster_path?.getPathWithBaseUrl() ?: "")
            binding.txtRate.text = tv.vote_average.toString()
        }

    }
}