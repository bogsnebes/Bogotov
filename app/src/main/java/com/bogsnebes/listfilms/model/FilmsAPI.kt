package com.bogsnebes.listfilms.model

import com.bogsnebes.listfilms.model.dto.DescriptionFilmDto
import com.bogsnebes.listfilms.model.dto.ListFilmsDto
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface FilmsAPI {
    @GET("api/v2.2/films/top?type=TOP_100_POPULAR_FILMS")
    fun getTopFilms(@Query("page") page: Int): Single<ListFilmsDto>

    @GET("api/v2.2/films/{filmID}")
    fun getFilmDescription(@Path("filmID") filmID: Int): Single<DescriptionFilmDto>
}