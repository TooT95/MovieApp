package com.example.movieapp.model

data class TV(
    val id: Long,
    val name: String,
    val poster_path: String?,
    val vote_average: Float,
    val backdrop_path: String?,
    val vote_count: Long,
    val first_air_date: String,
    val genres: List<Genre>,
    val overview: String?,
    val episode_run_time: List<Int>?,
)
