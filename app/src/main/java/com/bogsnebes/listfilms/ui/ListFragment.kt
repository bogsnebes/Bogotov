package com.bogsnebes.listfilms.ui

import android.annotation.SuppressLint
import android.content.res.Configuration
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.doAfterTextChanged
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import coil.load
import coil.transform.RoundedCornersTransformation
import com.bogsnebes.listfilms.R

class ListFragment : Fragment() {
    private val filmViewModel by lazy {
        ViewModelProvider(this)[FilmViewModel::class.java]
    }

    private var searchState = false
    private var favoritesState = false
    private var adapter: ListAdapter? = null
    private var searchQuery: String? = null

    private lateinit var headerTextView: TextView
    private lateinit var recyclerView: RecyclerView
    private lateinit var progressBar: ProgressBar
    private lateinit var dataLoadingErrorImageView: ImageView
    private lateinit var dataLoadingErrorTextView: TextView
    private lateinit var repeatButton: Button

    //    Поиск
    private lateinit var searchButton: ImageButton
    private lateinit var searchEditText: EditText
    private lateinit var backButton: ImageButton
    private lateinit var notFoundSearchView: Button

    //    Нижнее меню
    private lateinit var popularButton: Button
    private lateinit var favoritesButton: Button

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_list, container, false)

        if (savedInstanceState != null) {
            searchState = savedInstanceState.getBoolean(KEY_SEARCH_STATE, false)
            favoritesState = savedInstanceState.getBoolean(KEY_FAVORITES_STATE, false)
            searchQuery = savedInstanceState.getString(KEY_SEARCH_QUERY, null)
        }

        headerTextView = view.findViewById(R.id.header_text_view)
        searchButton = view.findViewById(R.id.search_button)
        searchEditText = view.findViewById(R.id.search_edit_text)
        backButton = view.findViewById(R.id.back_button)
        popularButton = view.findViewById(R.id.popular_button)
        favoritesButton = view.findViewById(R.id.favorites_button)
        recyclerView = view.findViewById(R.id.recyclerView)
        progressBar = view.findViewById(R.id.progress_bar)
        dataLoadingErrorImageView = view.findViewById(R.id.data_loading_error_image_view)
        dataLoadingErrorTextView = view.findViewById(R.id.data_loading_error_text_view)
        repeatButton = view.findViewById(R.id.repeat_button)
        notFoundSearchView = view.findViewById(R.id.not_found_search)

        recyclerView.layoutManager = LinearLayoutManager(context)

        displaySearch()
        displayFavorites()
        filmViewModel.getFilms()
        updateUI()

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

        repeatButton.setOnClickListener {
            filmViewModel.getFilms()
        }

        return view
    }

    override fun onStart() {
        super.onStart()

        searchEditText.doAfterTextChanged {
            if (it != null) {
                if (adapter != null) {
                    if ((adapter ?: return@doAfterTextChanged).filterList(it.toString())
                            .isNotEmpty()
                    ) {
                        notFoundSearchView.visibility = View.GONE
                        recyclerView.visibility = View.VISIBLE
                        val filteredAdapter: ListAdapter =
                            ListAdapter(
                                (adapter ?: return@doAfterTextChanged).filterList(it.toString())
                                    .toMutableList()
                            )
                        recyclerView.adapter = filteredAdapter
                    } else {
                        notFoundSearchView.visibility = View.VISIBLE
                        recyclerView.visibility = View.GONE
                    }
                }
            } else {
                recyclerView.adapter = adapter
            }
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putBoolean(KEY_SEARCH_STATE, searchState)
        outState.putBoolean(KEY_FAVORITES_STATE, favoritesState)
        outState.putString(KEY_SEARCH_QUERY, searchQuery)
    }

    private fun updateUI() {
        filmViewModel.filmsScreenState.observe(viewLifecycleOwner) { filmsScreenState ->
            when (filmsScreenState) {
                is FilmsScreenState.Loading -> {
                    hideError()
                    recyclerView.visibility = View.GONE
                    progressBar.visibility = View.VISIBLE
                }
                is FilmsScreenState.Result -> {
                    hideError()
                    progressBar.visibility = View.GONE
                    recyclerView.visibility = View.VISIBLE
                    adapter = ListAdapter(filmsScreenState.items.toMutableList())
                    recyclerView.adapter = adapter
                }
                is FilmsScreenState.Error -> {
                    displayError()
                }
            }
        }

        filmViewModel.descriptionScreenState.observe(viewLifecycleOwner) { descriptionScreenState ->
            val fragmentManager =
                (context as AppCompatActivity).supportFragmentManager
            val fragmentTransaction = fragmentManager.beginTransaction()

            when (descriptionScreenState) {
                is DescriptionScreenState.Loading -> {
                    recyclerView.visibility = View.GONE
                    progressBar.visibility = View.VISIBLE
                }
                is DescriptionScreenState.Result -> {
                    val fragment = DescriptionFragment.newInstance(descriptionScreenState.item)
                    if (resources.configuration.orientation == Configuration.ORIENTATION_LANDSCAPE) {
                        if (fragmentManager.findFragmentById(R.id.fragmentContainerView2) == null) {
                            fragmentTransaction.add(
                                R.id.fragmentContainerView2,
                                fragment,
                                DescriptionFragment.TAG
                            )
                        } else {
                            fragmentTransaction.replace(
                                R.id.fragmentContainerView2,
                                fragment,
                                DescriptionFragment.TAG
                            )
                        }
                        fragmentTransaction.addToBackStack(null)
                        fragmentTransaction.commit()
                    } else {
                        fragmentTransaction.replace(
                            R.id.fragment_container,
                            fragment,
                            DescriptionFragment.TAG
                        )
                        fragmentTransaction.addToBackStack(null)
                        fragmentTransaction.commit()
                    }
                    filmViewModel.descriptionStateNotWorking()
                }
                is DescriptionScreenState.Error -> {
                    recyclerView.visibility = View.VISIBLE
                    progressBar.visibility = View.GONE
                    Toast.makeText(context, "Ошибка подключения", Toast.LENGTH_SHORT).show()
                }
                else -> {
                    recyclerView.visibility = View.VISIBLE
                    progressBar.visibility = View.GONE
                }
            }
        }
    }

    private fun displayError() {
        recyclerView.visibility = View.GONE
        progressBar.visibility = View.GONE
        dataLoadingErrorImageView.visibility = View.VISIBLE
        dataLoadingErrorTextView.visibility = View.VISIBLE
        repeatButton.visibility = View.VISIBLE
    }

    private fun hideError() {
        dataLoadingErrorImageView.visibility = View.GONE
        dataLoadingErrorTextView.visibility = View.GONE
        repeatButton.visibility = View.GONE
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

    private inner class ListAdapter(val listFilms: MutableList<FilmCard>) :
        RecyclerView.Adapter<ListViewHolder>() {
        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListViewHolder {
            val view = layoutInflater.inflate(R.layout.item_list, parent, false)
            return ListViewHolder(view)
        }

        override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
            holder.bind(listFilms[position])
        }

        override fun getItemCount(): Int {
            return listFilms.size
        }

        fun filterList(query: String): List<FilmCard> {
            val filteredList = listFilms.filter { film ->
                film.title.contains(query, ignoreCase = true) ||
                        film.genre[0].contains(query, ignoreCase = true)
            }
            return filteredList
        }
    }

    private inner class ListViewHolder(view: View) : RecyclerView.ViewHolder(view),
        View.OnClickListener {
        private lateinit var filmCard: FilmCard
        private val cornerRadius = 10f

        private var bannerImageView: ImageView = view.findViewById(R.id.film_banner_image_view)
        private var titleTextView: TextView = view.findViewById(R.id.title_text_view)
        private var genreTextView: TextView = view.findViewById(R.id.genre_text_view)
        private var favoriteStarImageView: ImageView =
            view.findViewById(R.id.favorite_image_view)

        init {
            itemView.setOnClickListener(this)
        }

        @SuppressLint("SetTextI18n")
        fun bind(filmCard: FilmCard) {
            this.filmCard = filmCard
            bannerImageView.load(this.filmCard.image) {
                transformations(
                    RoundedCornersTransformation(
                        cornerRadius
                    )
                )
            }
            titleTextView.text = filmCard.title
            genreTextView.text = "${filmCard.genre[0]} (${filmCard.year})"
            if (this.filmCard.favorite)
                favoriteStarImageView.visibility = View.VISIBLE
        }

        override fun onClick(p0: View?) {
            filmViewModel.getDescription(this.filmCard.filmId)
        }
    }

    @Suppress("HardCodedStringLiteral")
    companion object {
        private const val KEY_SEARCH_STATE = "search state"
        private const val KEY_FAVORITES_STATE = "favorites state"
        private const val KEY_SEARCH_QUERY = "searchQuery"

        const val TAG = "ListFragment"

        fun newInstance(): ListFragment = ListFragment()
    }
}