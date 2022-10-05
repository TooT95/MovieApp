package com.example.movieapp.adapter

import android.annotation.SuppressLint
import androidx.recyclerview.widget.DiffUtil
import com.example.movieapp.model.*

class ObjectDiffUtil<T : Any> : DiffUtil.ItemCallback<T>() {
    override fun areItemsTheSame(oldItem: T, newItem: T): Boolean {
        return when (oldItem) {
            is Discover -> {
                newItem as Discover
                (oldItem.name == newItem.name) && (oldItem.isSelected == newItem.isSelected)
            }
            is Cast -> {
                newItem as Cast
                oldItem.id == newItem.id
            }
            is Genre -> {
                newItem as Genre
                oldItem.id == newItem.id
            }
            is Movie -> {
                newItem as Movie
                oldItem.id == newItem.id
            }
            is TV -> {
                newItem as TV
                oldItem.id == newItem.id
            }
            else -> false
        }
    }

    @SuppressLint("DiffUtilEquals")
    override fun areContentsTheSame(oldItem: T, newItem: T): Boolean {
        return oldItem == newItem
    }
}