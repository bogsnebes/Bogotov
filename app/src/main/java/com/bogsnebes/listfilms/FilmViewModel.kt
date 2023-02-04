package com.bogsnebes.listfilms

import android.util.Log
import androidx.lifecycle.ViewModel

class FilmViewModel : ViewModel() {
    init {
        Log.d(TAG, "${FilmViewModel::class.java} initialized")
    }

    companion object {
        private const val TAG = "FilmViewModel"
    }
}