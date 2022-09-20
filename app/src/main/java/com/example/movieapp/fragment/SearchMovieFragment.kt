package com.example.movieapp.fragment

import android.os.Bundle
import android.view.View
import android.widget.SearchView
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.movieapp.R
import com.example.movieapp.adapter.MovieSearchListAdapter
import com.example.movieapp.databinding.FragmentSearchBinding
import com.example.movieapp.utils.autoCleared
import com.example.movieapp.viewmodel.MovieListViewModel
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber

@AndroidEntryPoint
class SearchMovieFragment : BaseFragment<FragmentSearchBinding>(FragmentSearchBinding::inflate) {

    private val movieListAdapter: MovieSearchListAdapter by lazy{
        MovieSearchListAdapter {
            val args = Bundle().apply {
                putLong(MovieFragment.MOVIE_ID_KEY, movieListAdapter.currentList[it].id)
            }
            findNavController().navigate(R.id.action_searchMovieFragment_to_movieFragment, args)
        }
    }
    private val movieListViewModel: MovieListViewModel by viewModels()

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
        movieListViewModel.toastLiveData.observe(viewLifecycleOwner, ::toast)
    }

    private fun initUI() {
        binding.apply {
            rvMovieList.apply {
                adapter = movieListAdapter
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
                        movieListViewModel.getMovieListByQueryText(text ?: "")
                        return false
                    }

                    override fun onQueryTextChange(text: String?): Boolean {
                        Timber.d("onQueryTextChange $text")
                        return false
                    }

                })
            }
        }
    }

    private fun showPbMovieList(show: Boolean) {
        binding.apply {
            pbMovieList.isVisible = show
            rvMovieList.isVisible = !show
        }
    }

}