package com.bogsnebes.listfilms

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.ImageButton
import android.widget.TextView
import androidx.fragment.app.Fragment

class ListFragment : Fragment() {
    private var searchState = false
    private var favoritesState = false

    private lateinit var headerTextView: TextView

    //    Поиск
    private lateinit var searchButton: ImageButton
    private lateinit var searchEditText: EditText
    private lateinit var backButton: ImageButton

    //    Нижнее меню
    private lateinit var popularButton: Button
    private lateinit var favoritesButton: Button

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_list, container, false)

        headerTextView = view.findViewById(R.id.header_text_view)
        searchButton = view.findViewById(R.id.search_button)
        searchEditText = view.findViewById(R.id.search_edit_text)
        backButton = view.findViewById(R.id.back_button)
        popularButton = view.findViewById(R.id.popular_button)
        favoritesButton = view.findViewById(R.id.favorites_button)

        displaySearch()
        displayFavorites()

        searchButton.setOnClickListener {
            searchState = true
            displaySearch()
        }

        backButton.setOnClickListener {
            searchState = false
            displaySearch()
        }

        favoritesButton.setOnClickListener {
            favoritesState = true
            displayFavorites()
        }

        popularButton.setOnClickListener {
            favoritesState = false
            displayFavorites()
        }

        return view
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putBoolean(KEY_SEARCH_STATE, searchState)
        outState.putBoolean(KEY_FAVORITES_STATE, favoritesState)
    }

    private fun displaySearch() {
        if (searchState) {
            headerTextView.visibility = View.GONE
            searchButton.visibility = View.GONE

            searchEditText.visibility = View.VISIBLE
            backButton.visibility = View.VISIBLE
        } else {
            headerTextView.visibility = View.VISIBLE
            searchButton.visibility = View.VISIBLE

            searchEditText.visibility = View.GONE
            backButton.visibility = View.GONE
        }
    }

    private fun displayFavorites() {
        if (favoritesState) {
            headerTextView.text = getString(R.string.favorites_title)
        } else {
            headerTextView.text = getString(R.string.popular_title)
        }
    }

    @Suppress("HardCodedStringLiteral")
    companion object {
        private const val KEY_SEARCH_STATE = "search state"
        private const val KEY_FAVORITES_STATE = "favorites state"

        fun newInstance(): ListFragment = ListFragment()
    }
}