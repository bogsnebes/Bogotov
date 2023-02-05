package com.bogsnebes.listfilms.model.dto

import com.google.gson.annotations.SerializedName

data class GenreDto(
    @SerializedName("genre")
    val genre: String
)