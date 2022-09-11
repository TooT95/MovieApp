package com.example.movieapp.adapter

import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.movieapp.R
import com.example.movieapp.databinding.ItemGenreBinding
import com.example.movieapp.extensions.inflateLayout
import com.example.movieapp.model.Genre
import timber.log.Timber

class GenreListAdapter(private val onItemClicked: (itemId: Int) -> Unit) :
    ListAdapter<Genre, GenreListAdapter.GenreListHolder>(GenreDiffUtil()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GenreListHolder {
        return GenreListHolder(onItemClicked, parent.inflateLayout(R.layout.item_genre))
    }

    override fun onBindViewHolder(holder: GenreListHolder, position: Int) {
        holder.onBind(currentList[position])
    }

    override fun getItemCount() = currentList.size

    class GenreListHolder(
        private val onItemClicked: (itemId: Int) -> Unit, view: View
    ) : RecyclerView.ViewHolder(view) {

        init {
            itemView.setOnClickListener {
                onItemClicked(adapterPosition)
            }
        }

        private val binding = ItemGenreBinding.bind(view)
        fun onBind(genre: Genre) {
            Timber.d("Bind genre ${genre.name}")
            binding.txtGenreName.text = genre.name
        }

    }

    class GenreDiffUtil : DiffUtil.ItemCallback<Genre>() {
        override fun areItemsTheSame(oldItem: Genre, newItem: Genre) = (oldItem.id == newItem.id)

        override fun areContentsTheSame(oldItem: Genre, newItem: Genre) = (oldItem == newItem)
    }

}