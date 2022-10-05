package com.example.movieapp.adapter

import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.movieapp.R
import com.example.movieapp.databinding.ItemDiscoverBinding
import com.example.movieapp.extensions.inflateLayout
import com.example.movieapp.model.Discover

class DiscoverListAdapter(private val onItemClicked: (itemId: Int) -> Unit) :
    ListAdapter<Discover, DiscoverListAdapter.DiscoverListHolder>(ObjectDiffUtil<Discover>()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DiscoverListHolder {
        return DiscoverListHolder(onItemClicked, parent.inflateLayout(R.layout.item_discover))
    }

    override fun onBindViewHolder(holder: DiscoverListHolder, position: Int) {
        holder.onBind(currentList[position])
    }

    override fun getItemCount() = currentList.size

    class DiscoverListHolder(private val onItemClicked: (itemId: Int) -> Unit, view: View) :
        RecyclerView.ViewHolder(view) {

        init {
            itemView.setOnClickListener {
                onItemClicked(adapterPosition)
            }
        }

        private val binding = ItemDiscoverBinding.bind(view)
        fun onBind(discover: Discover) {
            with(binding) {
                txtDiscoverTitle.text = discover.name
                ivRec.isVisible = discover.isSelected
                if (discover.isSelected)
                    txtDiscoverTitle.setTextAppearance(R.style.CustomHeadline1)
                else txtDiscoverTitle.setTextAppearance(
                    R.style.CustomHeadline2)
            }
        }

    }

}