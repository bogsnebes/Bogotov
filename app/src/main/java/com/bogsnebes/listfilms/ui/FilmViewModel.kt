package com.bogsnebes.listfilms.ui

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.bogsnebes.listfilms.model.FilmsImpl
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.addTo
import io.reactivex.schedulers.Schedulers

class FilmViewModel : ViewModel() {
    init {
        Log.d(TAG, "${FilmViewModel::class.java} initialized")
    }

    private var cacheOfFilms = mutableListOf<FilmCard>()

    private var _filmsScreenState: MutableLiveData<FilmsScreenState> = MutableLiveData()
    val filmsScreenState: LiveData<FilmsScreenState>
        get() = _filmsScreenState

    private var _descriptionScreenState: MutableLiveData<DescriptionScreenState> = MutableLiveData()
    val descriptionScreenState: LiveData<DescriptionScreenState>
        get() = _descriptionScreenState

    private val filmsImpl: FilmsImpl = FilmsImpl()
    private val compositeDisposable: CompositeDisposable = CompositeDisposable()

    fun getFilms() {
        _filmsScreenState.value = FilmsScreenState.Loading
        filmsImpl.getFilms()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({ films ->
                val filmCards = films.map {
                    FilmCard(
                        it.filmId,
                        it.image,
                        it.title,
                        it.genres.map {
                            it.genre
                        },
                        it.year
                    )
                }
                _filmsScreenState.value = FilmsScreenState.Result(filmCards)
                cacheOfFilms.addAll(filmCards)
            }, { error ->
                if (cacheOfFilms.isEmpty())
                    _filmsScreenState.value = FilmsScreenState.Error(error)
                else
                    _filmsScreenState.value = FilmsScreenState.Result(cacheOfFilms)
            })
            .addTo(compositeDisposable)
    }

    fun getDescription(filmId: Int) {
        _descriptionScreenState.value = DescriptionScreenState.Loading
        filmsImpl.getDescription(filmId)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                { _descriptionScreenState.value = DescriptionScreenState.Result(it) },
                { _descriptionScreenState.value = DescriptionScreenState.Error(it) }
            )
            .addTo(compositeDisposable)
    }

    fun descriptionStateNotWorking() {
        _descriptionScreenState.value = DescriptionScreenState.NotWorking
    }

    override fun onCleared() {
        super.onCleared()
        compositeDisposable.dispose()
    }

    companion object {
        private const val TAG = "FilmViewModel"
    }
}