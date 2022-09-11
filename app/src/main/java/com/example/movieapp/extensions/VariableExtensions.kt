package com.example.movieapp.extensions

import com.example.movieapp.model.Discover
import com.example.movieapp.utils.Utils

fun String.getPathWithBaseUrl(): String {
    return "${Utils.BASE_IMAGE_URL}$this"
}

fun List<Discover>.isMovie(): Boolean {
    return this.filter {
        it.isSelected
    }.last().name == "In Theaters"
}

fun List<Discover>.selectedElement(): Discover {
    return this.filter {
        it.isSelected
    }.last()
}