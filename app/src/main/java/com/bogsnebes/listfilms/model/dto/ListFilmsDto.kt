package com.bogsnebes.listfilms.model.dto

import com.google.gson.annotations.SerializedName

data class ListFilmsDto(
    @SerializedName("films")
    val films: List<FilmDto>
)
