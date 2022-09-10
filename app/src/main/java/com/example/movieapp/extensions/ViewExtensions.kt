package com.example.movieapp.extensions

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.annotation.LayoutRes
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide

fun ViewGroup.inflateLayout(
    @LayoutRes layoutId: Int,
    attachToParent: Boolean = false,
): View {
    return LayoutInflater.from(context).inflate(layoutId, this, attachToParent)
}

fun ImageView.glideImage(view: View, url: String) {
    with(this) {
        Glide.with(view)
            .load(url)
            .into(this)
    }
}