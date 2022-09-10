package com.example.movieapp.extensions

import com.example.movieapp.utils.Utils

fun String.getPathWithBaseUrl(): String {
    return "${Utils.BASE_IMAGE_URL}$this"
}