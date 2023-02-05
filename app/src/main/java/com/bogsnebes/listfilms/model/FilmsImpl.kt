package com.bogsnebes.listfilms.model

import com.bogsnebes.listfilms.App.Companion.retrofit
import com.bogsnebes.listfilms.model.dto.DescriptionFilmDto
import com.bogsnebes.listfilms.model.dto.FilmDto
import com.bogsnebes.listfilms.model.dto.ListFilmsDto
import io.reactivex.Single

class FilmsImpl {
    private val filmsApi = retrofit.create(FilmsAPI::class.java)

    fun getFilms(page: Int): Single<List<FilmDto>> {
        return loadFilms(page).map { it.films }
    }

    fun getDescription(filmId: Int): Single<DescriptionFilmDto> {
        return loadDescription(filmId)
    }

    private fun loadFilms(page: Int): Single<ListFilmsDto> {
        return filmsApi.getTopFilms(page)
    }

    private fun loadDescription(filmId: Int): Single<DescriptionFilmDto> {
        return filmsApi.getFilmDescription(filmId)
    }
}