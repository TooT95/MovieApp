package com.example.movieapp.fragment

import android.os.Bundle
import android.view.View
import android.widget.SearchView
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.movieapp.R
import com.example.movieapp.adapter.SearchMovieListAdapter
import com.example.movieapp.adapter.SearchTvListAdapter
import com.example.movieapp.databinding.FragmentSearchBinding
import com.example.movieapp.viewmodel.MovieListViewModel
import com.example.movieapp.viewmodel.TVListViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SearchFragment : BaseFragment<FragmentSearchBinding>(FragmentSearchBinding::inflate) {

    private val movieListAdapter: SearchMovieListAdapter by lazy {
        SearchMovieListAdapter {
            val args = Bundle().apply {
                putLong(MovieFragment.MOVIE_ID_KEY, movieListAdapter.currentList[it].id)
            }
            findNavController().navigate(R.id.action_searchMovieFragment_to_movieFragment, args)
        }
    }
    private val tvListAdapter: SearchTvListAdapter by lazy {
        SearchTvListAdapter {
            val args = Bundle().apply {
                putLong(TVFragment.TV_ID_KEY, tvListAdapter.currentList[it].id)
            }
            findNavController().navigate(R.id.action_searchMovieFragment_to_TVFragment, args)
        }
    }
    private val movieListViewModel: MovieListViewModel by viewModels()
    private val tvListViewModel: TVListViewModel by viewModels()

    private var isMovie: Boolean = false

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initUI()
        observeViewModel()
    }

    private fun observeViewModel() {
        movieListViewModel.movieListLiveData.observe(viewLifecycleOwner) {
            showPbMovieList(false)
            movieListAdapter.submitList(it)
        }
        tvListViewModel.tvListLiveData.observe(viewLifecycleOwner) {
            showPbMovieList(false)
            tvListAdapter.submitList(it)
        }
        movieListViewModel.toastLiveData.observe(viewLifecycleOwner, ::toast)
    }

    private fun initUI() {
        arguments?.let {
            isMovie = it.getBoolean(MOVIE_TV, false)
        }
        with(binding) {
            with(rvMovieList) {
                adapter = if (isMovie)
                    movieListAdapter
                else
                    tvListAdapter
                setHasFixedSize(true)
                layoutManager = LinearLayoutManager(requireContext())
            }
            toolbar.setNavigationOnClickListener {
                activity?.onBackPressed()
            }
            with(svMovieTv) {
                isFocusable = true
                requestFocusFromTouch()
                setOnQueryTextListener(object :
                    SearchView.OnQueryTextListener {
                    override fun onQueryTextSubmit(text: String?): Boolean {
                        showPbMovieList(true)
                        if (isMovie)
                            movieListViewModel.getMovieListByQueryText(text ?: "")
                        else
                            tvListViewModel.getTvListByQueryText(text ?: "")
                        return false
                    }

                    override fun onQueryTextChange(text: String?): Boolean {
                        return false
                    }
                })
            }
        }
    }

    private fun showPbMovieList(show: Boolean) {
        with(binding) {
            pbMovieList.isVisible = show
            rvMovieList.isVisible = !show
        }
    }

    companion object {
        const val MOVIE_TV = "movie tv"
    }

    override fun viewCreated() {

    }

}