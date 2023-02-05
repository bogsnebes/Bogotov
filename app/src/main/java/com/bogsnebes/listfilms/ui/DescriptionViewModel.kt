package com.bogsnebes.listfilms.ui

import androidx.lifecycle.ViewModel
import com.bogsnebes.listfilms.model.dto.DescriptionFilmDto

class DescriptionViewModel : ViewModel() {

    lateinit var descriptionFilm: DescriptionFilmDto

    fun saveDescriptionFilm(descriptionFilmDto: DescriptionFilmDto) {
        descriptionFilm = descriptionFilmDto
    }
}