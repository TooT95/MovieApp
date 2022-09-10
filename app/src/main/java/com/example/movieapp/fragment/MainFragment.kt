package com.example.movieapp.fragment

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.movieapp.R
import com.example.movieapp.adapter.DiscoverListAdapter
import com.example.movieapp.adapter.GenreListAdapter
import com.example.movieapp.adapter.MovieListAdapter
import com.example.movieapp.databinding.FragmentMainBinding
import com.example.movieapp.model.Discover
import com.example.movieapp.model.Genre
import com.example.movieapp.model.Movie
import com.example.movieapp.utils.autoCleared
import com.example.movieapp.viewmodel.GenreListViewModel
import dagger.hilt.android.AndroidEntryPoint


class MainFragment : BaseFragment<FragmentMainBinding>(FragmentMainBinding::inflate) {

    private var discoverListAdapter: DiscoverListAdapter by autoCleared()
    private val genreListAdapter: GenreListAdapter by lazy {
        GenreListAdapter()
    }
    private var movieListAdapter: MovieListAdapter by autoCleared()

    private val genreViewModel: GenreListViewModel by viewModels()

    private val currentDiscoverList = listOf(Discover("In theatre", true),
        Discover("In TV", false),
        Discover("Box Office", false))
    private val currentMovieList = listOf(
        Movie(0, "Thor: Love and Thunder", 6.8f, "/pIkRyD18kl4FhoCNQuWxWu5cBLM.jpg"),
        Movie(1, "The Black Phone", 7.9f, "/lr11mCT85T1JanlgjMuhs9nMht4.jpg"),
        Movie(2, "Day Shift", 6.9f, "/bI7lGR5HuYlENlp11brKUAaPHuO.jpg"),
        Movie(3, "Your Boyfriend Is Mine", 6.4f, "/2OOYNZLKjdX8Z5KNyz7zZnHmodJ.jpg")
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        genreViewModel.getGenreList()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initUI()
        observeViewModels()
    }

    private fun observeViewModels() {
        genreViewModel.genreListLiveData.observe(viewLifecycleOwner) {
            genreListAdapter.submitList(it)
        }
        genreViewModel.toastLiveData.observe(viewLifecycleOwner, ::toast)
    }

    private fun initMovieList() {
        movieListAdapter = MovieListAdapter {
            findNavController().navigate(R.id.action_mainFragment_to_movieFragment)
        }.apply {
            movieList = currentMovieList
        }
        with(binding.rvMovieList) {
            adapter = movieListAdapter
            setHasFixedSize(true)
            layoutManager =
                LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        }
    }

    private fun initUI() {
        initGenreLIst()
        initDiscoverLIst()
        initMovieList()
    }

    private fun initDiscoverLIst() {
        discoverListAdapter = DiscoverListAdapter().apply {
            discoverList = currentDiscoverList
        }
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
            setHasFixedSize(true)
            layoutManager =
                LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        }
    }

}