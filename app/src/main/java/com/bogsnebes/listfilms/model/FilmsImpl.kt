package com.bogsnebes.listfilms.model

import com.bogsnebes.listfilms.App.Companion.retrofit
import com.bogsnebes.listfilms.model.dto.DescriptionFilmDto
import com.bogsnebes.listfilms.model.dto.FilmDto
import com.bogsnebes.listfilms.model.dto.ListFilmsDto
import io.reactivex.Single

class FilmsImpl {
    private val filmsApi = retrofit.create(FilmsAPI::class.java)

    fun getFilms(): Single<List<FilmDto>> {
        return loadFilms().map { it.films }
    }

    fun getDescription(filmId: Int): Single<DescriptionFilmDto> {
        return loadDescription(filmId)
    }

    private fun loadFilms(): Single<ListFilmsDto> {
        return filmsApi.getTopFilms()
    }

    private fun loadDescription(filmId: Int): Single<DescriptionFilmDto> {
        return filmsApi.getFilmDescription(filmId)
    }
}