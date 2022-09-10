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
import com.example.movieapp.adapter.TVListAdapter
import com.example.movieapp.databinding.FragmentMainBinding
import com.example.movieapp.model.Discover
import com.example.movieapp.utils.Utils
import com.example.movieapp.viewmodel.GenreListViewModel
import com.example.movieapp.viewmodel.MovieListViewModel
import com.example.movieapp.viewmodel.TVListViewModel
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber

@AndroidEntryPoint
class MainFragment : BaseFragment<FragmentMainBinding>(FragmentMainBinding::inflate) {

    private val genreViewModel: GenreListViewModel by viewModels()
    private val movieViewModel: MovieListViewModel by viewModels()
    private val tvViewModel: TVListViewModel by viewModels()

    private val discoverListAdapter: DiscoverListAdapter by lazy {
        DiscoverListAdapter {
            when (it) {
                1 -> tvViewModel.getPopularTVList()
                else -> movieViewModel.getPopularMovieList()
            }
            initDiscoverList(Utils.currentDiscoverList[it])
        }.apply {
            submitList(Utils.currentDiscoverList)
        }
    }
    private val genreListAdapter: GenreListAdapter by lazy {
        GenreListAdapter().apply {
            submitList(emptyList())
        }
    }
    private val movieListAdapter: MovieListAdapter by lazy {
        MovieListAdapter {
            findNavController().navigate(R.id.action_mainFragment_to_movieFragment)
        }
    }
    private val tvListAdapter: TVListAdapter by lazy {
        TVListAdapter {
            findNavController().navigate(R.id.action_mainFragment_to_movieFragment)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        genreViewModel.getGenreList()
        movieViewModel.getPopularMovieList()
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
        }
        movieViewModel.movieListLiveData.observe(viewLifecycleOwner) {
            movieListAdapter.submitList(it)
        }
        tvViewModel.tvListLiveData.observe(viewLifecycleOwner) {
            tvListAdapter.submitList(it)
        }
        genreViewModel.toastLiveData.observe(viewLifecycleOwner, ::toast)
        movieViewModel.toastLiveData.observe(viewLifecycleOwner, ::toast)
        tvViewModel.toastLiveData.observe(viewLifecycleOwner, ::toast)
    }

    private fun initUI() {
        initGenreLIst()
        initDiscoverLIst()
        initDiscoverList(Utils.currentDiscoverList[0])
    }

    private fun initDiscoverList(discover: Discover) {
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

}