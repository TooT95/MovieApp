package com.example.movieapp.extensions

import android.content.Context
import android.graphics.drawable.Drawable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.annotation.LayoutRes
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.movieapp.R

fun ViewGroup.inflateLayout(
    @LayoutRes layoutId: Int,
    attachToParent: Boolean = false,
): View {
    return LayoutInflater.from(context).inflate(layoutId, this, attachToParent)
}

fun ImageView.glideImage(view: View, url: String, placeHolder: Int = R.drawable.ic_avatar) {
    with(this) {
        Glide.with(view)
            .load(url)
            .placeholder(placeHolder)
            .into(this)
    }
}

fun ImageView.glideImage(context: Context, url: String) {
    with(this) {
        Glide.with(context)
            .load(url)
            .into(this)
    }
}