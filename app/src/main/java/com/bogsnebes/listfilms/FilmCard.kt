package com.bogsnebes.listfilms

import android.net.Uri

data class FilmCard(
    val image: Uri,
    val title: String,
    val genre: String,
    val date: Int
)
