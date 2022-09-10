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

class DiscoverListAdapter : RecyclerView.Adapter<DiscoverListAdapter.DiscoverListHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DiscoverListHolder {
        return DiscoverListHolder(parent.inflateLayout(R.layout.item_discover))
    }

    override fun onBindViewHolder(holder: DiscoverListHolder, position: Int) {
        holder.onBind(discoverList[position])
    }

    override fun getItemCount() = discoverList.size

    var discoverList = emptyList<Discover>()

    class DiscoverListHolder(view: View) : RecyclerView.ViewHolder(view) {

        private val binding = ItemDiscoverBinding.bind(view)
        fun onBind(discover: Discover) {
            binding.txtDiscoverTitle.text = discover.name
            binding.ivRec.isVisible = discover.isSelected
            if (discover.isSelected)
                binding.txtDiscoverTitle.setTextAppearance(R.style.CustomHeadline1)
            else binding.txtDiscoverTitle.setTextAppearance(
                R.style.CustomHeadline2)
        }

    }

}