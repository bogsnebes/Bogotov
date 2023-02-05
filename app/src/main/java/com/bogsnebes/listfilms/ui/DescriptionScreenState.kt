package com.bogsnebes.listfilms.ui

import android.util.Log
import com.bogsnebes.listfilms.model.dto.DescriptionFilmDto

sealed class DescriptionScreenState {
    class Result(val item: DescriptionFilmDto) : DescriptionScreenState()

    object Loading : DescriptionScreenState()

    object NotWorking : DescriptionScreenState()

    class Error(private val error: Throwable) : DescriptionScreenState() {
        init {
            Log.e(TAG, "Error: ", error)
        }
    }

    companion object {
        private const val TAG = "network"
    }
}