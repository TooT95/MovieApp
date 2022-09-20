package com.example.movieapp.fragment

import android.os.Bundle
import android.view.View
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.movieapp.R
import com.example.movieapp.adapter.DiscoverListAdapter
import com.example.movieapp.adapter.GenreListAdapter
import com.example.movieapp.adapter.MovieListAdapter
import com.example.movieapp.adapter.TVListAdapter
import com.example.movieapp.databinding.FragmentMainBinding
import com.example.movieapp.extensions.isMovie
import com.example.movieapp.extensions.selectedElement
import com.example.movieapp.model.Discover
import com.example.movieapp.viewmodel.GenreListViewModel
import com.example.movieapp.viewmodel.MovieListViewModel
import com.example.movieapp.viewmodel.TVListViewModel
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber

@AndroidEntryPoint
class MainFragment : BaseFragment<FragmentMainBinding>(FragmentMainBinding::inflate) {

    private var currentDiscoverList = listOf(
        Discover("In Theaters", true),
        Discover("On TV", false)
    )

    private val genreViewModel: GenreListViewModel by viewModels()
    private val movieViewModel: MovieListViewModel by viewModels()
    private val tvViewModel: TVListViewModel by viewModels()

    private val discoverListAdapter: DiscoverListAdapter by lazy {
        DiscoverListAdapter {
            onDiscoverItemClicked(it)
        }.apply {
            submitList(currentDiscoverList)
        }
    }
    private val genreListAdapter: GenreListAdapter by lazy {
        GenreListAdapter {
            onGenreItemClicked(it)
        }.apply {
            submitList(emptyList())
        }
    }

    private val movieListAdapter: MovieListAdapter by lazy {
        MovieListAdapter {
            val args = Bundle().apply {
                putLong(MovieFragment.MOVIE_ID_KEY, movieListAdapter.currentList[it].id)
            }
            findNavController().navigate(R.id.action_mainFragment_to_movieFragment, args)
        }
    }
    private val tvListAdapter: TVListAdapter by lazy {
        TVListAdapter {
            val args = Bundle().apply {
                putLong(TVFragment.TV_ID_KEY, tvListAdapter.currentList[it].id)
            }
            findNavController().navigate(R.id.action_mainFragment_to_TVFragment, args)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        genreViewModel.getGenreMovieList()
        movieViewModel.getPopularMovieList(null)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initUI()
        observeViewModels()
    }

    private fun observeViewModels() {
        genreViewModel.genreListLiveData.observe(viewLifecycleOwner) {
            Timber.d("Genre list size ${it.size}")
            genreListAdapter.submitList(it)
            showProgressBarGenre(false)
        }
        movieViewModel.movieListLiveData.observe(viewLifecycleOwner) {
            movieListAdapter.submitList(it)
            showProgressBarMovie(false)
        }
        tvViewModel.tvListLiveData.observe(viewLifecycleOwner) {
            tvListAdapter.submitList(it)
            showProgressBarMovie(false)
        }
        genreViewModel.toastLiveData.observe(viewLifecycleOwner, ::toast)
        movieViewModel.toastLiveData.observe(viewLifecycleOwner, ::toast)
        tvViewModel.toastLiveData.observe(viewLifecycleOwner, ::toast)
    }

    private fun initUI() {
        binding.inToolbar.toolbar.apply {
            setNavigationIcon(R.drawable.ic_menu)
            inflateMenu(R.menu.menu_main)
            setOnMenuItemClickListener { menuItem ->
                if (menuItem.itemId == R.id.item_search) {
                    val args = Bundle().apply {
                        putBoolean(SearchFragment.MOVIE_TV, currentDiscoverList.isMovie())
                    }
                    findNavController().navigate(R.id.action_mainFragment_to_searchMovieFragment,
                        args)
                }
                return@setOnMenuItemClickListener false
            }
        }
        initGenreLIst()
        initDiscoverLIst()
        initDiscoverList()
        showProgressBarGenre(true)
        showProgressBarMovie(true)
    }

    private fun initDiscoverList() {
        val discover = currentDiscoverList.selectedElement()
        when (discover.name) {
            "In Theaters" -> with(binding.rvMovieList) {
                adapter = movieListAdapter
                setHasFixedSize(true)
                layoutManager =
                    LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
            }
            "On TV" -> with(binding.rvMovieList) {
                adapter = tvListAdapter
                setHasFixedSize(true)
                layoutManager =
                    LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
            }
        }
    }

    private fun initDiscoverLIst() {
        with(binding.rvDiscoverList) {
            adapter = discoverListAdapter
            setHasFixedSize(true)
            layoutManager =
                LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        }
    }

    private fun initGenreLIst() {
        binding.rvGenreList.apply {
            adapter = genreListAdapter
            layoutManager =
                LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        }
    }

    private fun onDiscoverItemClicked(indexBySelected: Int) {
        showProgressBarGenre(true)
        showProgressBarMovie(true)
        currentDiscoverList = currentDiscoverList.mapIndexed { index, discover ->
            Discover(discover.name, (index == indexBySelected))
        }
        discoverListAdapter.submitList(currentDiscoverList)

        if (currentDiscoverList.isMovie()) genreViewModel.getGenreMovieList() else genreViewModel.getGenreTVList()

        when (indexBySelected) {
            1 -> {
                tvViewModel.getPopularTVList(null)
            }
            else -> movieViewModel.getPopularMovieList(null)
        }
        initDiscoverList()
    }

    private fun onGenreItemClicked(indexBySelected: Int) {
        showProgressBarMovie(true)
        val genre = genreListAdapter.currentList[indexBySelected]
        if (currentDiscoverList.isMovie())
            movieViewModel.getPopularMovieList(genre.id)
        else
            tvViewModel.getPopularTVList(genre.id)
        initDiscoverList()
    }

    private fun showProgressBarGenre(show: Boolean) {
        binding.apply {
            pbGenreList.isVisible = show
            rvGenreList.isVisible = !show
        }
    }

    private fun showProgressBarMovie(show: Boolean) {
        binding.apply {
            pbMovieList.isVisible = show
            rvMovieList.isVisible = !show
        }
    }
}