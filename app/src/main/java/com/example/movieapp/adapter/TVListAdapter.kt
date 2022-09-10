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
import com.example.movieapp.model.TV

class TVListAdapter(private val onItemClicked: (itemId: Int) -> Unit) :
    ListAdapter<TV, TVListAdapter.TVListHolder>(MovieDiffUtil()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TVListHolder {
        return TVListHolder(onItemClicked, parent.inflateLayout(R.layout.item_list_movie_tv))
    }

    override fun onBindViewHolder(holder: TVListHolder, position: Int) {
        holder.onBind(currentList[position])
    }

    override fun getItemCount() = currentList.size

    class TVListHolder(private val onItemClicked: (itemId: Int) -> Unit, view: View) :
        RecyclerView.ViewHolder(view) {

        init {
            itemView.setOnClickListener {
                onItemClicked(adapterPosition)
            }
        }

        private val binding = ItemListMovieTvBinding.bind(view)
        fun onBind(TV: TV) {
            binding.txtMovieName.text = TV.name
            binding.ivMovieIcon.glideImage(itemView, TV.poster_path.getPathWithBaseUrl())
            binding.txtRate.text = TV.vote_average.toString()
        }

    }

    class MovieDiffUtil : DiffUtil.ItemCallback<TV>() {
        override fun areItemsTheSame(oldItem: TV, newItem: TV) = (oldItem.id == newItem.id)

        override fun areContentsTheSame(oldItem: TV, newItem: TV) = (oldItem == newItem)

    }
}