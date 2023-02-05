package com.bogsnebes.listfilms.model.dto

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class DescriptionFilmDto(
    @SerializedName("kinopoiskId")
    val filmId: Int,
    @SerializedName("posterUrl")
    val image: String,
    @SerializedName("nameRu")
    val title: String,
    @SerializedName("description")
    val description: String,
    @SerializedName("genres")
    val genres: List<GenreDto>,
    @SerializedName("countries")
    val countries: List<CountriesDto>,
    @SerializedName("year")
    val year: Int,
) : Serializable
