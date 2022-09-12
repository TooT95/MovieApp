package com.example.movieapp.model

data class Movie(
    val id: Long,
    val title: String,
    val vote_average: Float,
    val poster_path: String,
    val backdrop_path: String,
    val vote_count: Long,
    val release_date: String,
    val runtime: Int,
    val genres: List<Genre>,
)
