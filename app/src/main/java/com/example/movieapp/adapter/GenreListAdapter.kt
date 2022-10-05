package com.example.movieapp.adapter

import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.movieapp.R
import com.example.movieapp.databinding.ItemGenreBinding
import com.example.movieapp.extensions.inflateLayout
import com.example.movieapp.model.Genre

class GenreListAdapter(private val onItemClicked: (itemId: Int) -> Unit) :
    ListAdapter<Genre, GenreListAdapter.GenreListHolder>(ObjectDiffUtil<Genre>()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GenreListHolder {
        return GenreListHolder(onItemClicked, parent.inflateLayout(R.layout.item_genre))
    }

    override fun onBindViewHolder(holder: GenreListHolder, position: Int) {
        holder.onBind(currentList[position])
    }

    override fun getItemCount() = currentList.size

    class GenreListHolder(
        private val onItemClicked: (itemId: Int) -> Unit, view: View,
    ) : RecyclerView.ViewHolder(view) {

        init {
            itemView.setOnClickListener {
                onItemClicked(adapterPosition)
            }
        }

        private val binding = ItemGenreBinding.bind(view)
        fun onBind(genre: Genre) {
            binding.txtGenreName.text = genre.name
        }

    }

}