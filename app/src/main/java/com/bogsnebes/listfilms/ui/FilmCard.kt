package com.bogsnebes.listfilms.ui

data class FilmCard(
    val filmId: Int,
    val image: String,
    val title: String,
    val genre: List<String>,
    val year: Int,
    val favorite: Boolean = false
)
