package com.bogsnebes.listfilms.model.dto

import com.google.gson.annotations.SerializedName

data class CountriesDto(
    @SerializedName("country")
    val country: String
)