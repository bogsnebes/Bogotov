package com.bogsnebes.listfilms.model.dto

import com.google.gson.annotations.SerializedName

data class FilmDto(
    @SerializedName("filmId")
    val filmId: Int,
    @SerializedName("posterUrlPreview")
    val image: String,
    @SerializedName("nameRu")
    val title: String,
    @SerializedName("genres")
    val genres: List<GenreDto>,
    @SerializedName("year")
    val year: Int,
)

