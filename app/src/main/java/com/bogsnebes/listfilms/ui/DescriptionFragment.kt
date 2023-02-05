package com.bogsnebes.listfilms.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import coil.load
import com.bogsnebes.listfilms.R
import com.bogsnebes.listfilms.model.dto.DescriptionFilmDto

class DescriptionFragment : Fragment() {
    private val descriptionViewModel by lazy {
        ViewModelProvider(this)[DescriptionViewModel::class.java]
    }

    private var descriptionFilm: DescriptionFilmDto? = null

    private lateinit var backImageButton: ImageButton
    private lateinit var posterImageView: ImageView
    private lateinit var headerTitleTextView: TextView
    private lateinit var descriptionTextView: TextView
    private lateinit var genreTextView: TextView
    private lateinit var countryTextView: TextView

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_description, container, false)

        if (descriptionFilm != null) {
            descriptionViewModel.saveDescriptionFilm(descriptionFilm!!)
        } else {
            descriptionFilm = descriptionViewModel.descriptionFilm
        }

        backImageButton = view.findViewById(R.id.back_to_list_button)
        posterImageView = view.findViewById(R.id.film_poster_image_view)
        headerTitleTextView = view.findViewById(R.id.header_title_text_view)
        descriptionTextView = view.findViewById(R.id.description_text_view)
        genreTextView = view.findViewById(R.id.genre_text_view)
        countryTextView = view.findViewById(R.id.county_text_view)

        updateUI()

        backImageButton.setOnClickListener {
            (context as AppCompatActivity).supportFragmentManager.popBackStack()
        }

        return view
    }

    private fun updateUI() {
        descriptionFilm.let {
            posterImageView.load(descriptionFilm!!.image)
            headerTitleTextView.text = descriptionFilm!!.title
            descriptionTextView.text = descriptionFilm!!.description
            genreTextView.text =
                descriptionFilm!!.genres.joinToString(", ") { it.genre }
            countryTextView.text =
                descriptionFilm!!.countries.joinToString(", ") { it.country }
        }
    }

    companion object {
        const val TAG = "DescriptionFragment"

        fun newInstance(descriptionFilm: DescriptionFilmDto): DescriptionFragment {
            val fragment = DescriptionFragment()
            fragment.descriptionFilm = descriptionFilm
            return fragment
        }
    }
}