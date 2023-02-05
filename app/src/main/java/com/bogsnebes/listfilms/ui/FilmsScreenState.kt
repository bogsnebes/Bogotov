package com.bogsnebes.listfilms.ui

import android.util.Log

sealed class FilmsScreenState {
    class Result(val items: List<FilmCard>) : FilmsScreenState()

    object Loading : FilmsScreenState()

    class Error(private val error: Throwable) : FilmsScreenState() {
        init {
            Log.e(TAG, "Error:", error)
        }
    }

    companion object {
        private const val TAG = "network"
    }
}
