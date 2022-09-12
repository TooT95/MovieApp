package com.example.movieapp.adapter

import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.movieapp.R
import com.example.movieapp.databinding.ItemCastBinding
import com.example.movieapp.extensions.getPathWithBaseUrl
import com.example.movieapp.extensions.glideImage
import com.example.movieapp.extensions.inflateLayout
import com.example.movieapp.model.Cast

class CastListAdapter : ListAdapter<Cast, CastListAdapter.CastListHolder>(CastDiffUtil()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CastListHolder {
        return CastListHolder(parent.inflateLayout(R.layout.item_cast))
    }

    override fun onBindViewHolder(holder: CastListHolder, position: Int) {
        holder.onBind(currentList[position])
    }

    override fun getItemCount() = currentList.size

    class CastDiffUtil : DiffUtil.ItemCallback<Cast>() {
        override fun areItemsTheSame(oldItem: Cast, newItem: Cast) = (oldItem.id == newItem.id)

        override fun areContentsTheSame(oldItem: Cast, newItem: Cast) = (oldItem == newItem)
    }

    class CastListHolder(view: View) : RecyclerView.ViewHolder(view) {

        private val itemBinding = ItemCastBinding.bind(view)
        fun onBind(cast: Cast) {
            itemBinding.apply {
                ivCastPoster.glideImage(itemView, cast.profile_path?.getPathWithBaseUrl() ?: "")
                txtCharacter.text = cast.character
                txtName.text = cast.name
            }
        }
    }
}