package com.example.movieapp.fragment

import android.os.Bundle
import android.view.View
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.movieapp.adapter.GenreListAdapter
import com.example.movieapp.databinding.FragmentMovieBinding
import com.example.movieapp.extensions.getPathWithBaseUrl
import com.example.movieapp.extensions.getRuntimeText
import com.example.movieapp.extensions.glideImage
import com.example.movieapp.viewmodel.MovieViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MovieFragment : BaseFragment<FragmentMovieBinding>(FragmentMovieBinding::inflate) {

    private val movieViewModel: MovieViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            movieViewModel.getMovie(it.getLong(MOVIE_ID_KEY))
        }

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initUI()
        observeViewModel()
    }

    private fun initUI() {
        binding.toolbar.setNavigationOnClickListener {
            activity?.onBackPressed()
        }
    }

    private fun observeViewModel() {
        movieViewModel.movieLiveData.observe(viewLifecycleOwner) {
            it?.let { movie ->
                binding.apply {
                    ivMovieIcon.glideImage(requireContext(),
                        movie.backdrop_path.getPathWithBaseUrl())
                    txtVoteAverage.text = movie.vote_average.toString()
                    txtVoteCount.text = movie.vote_count.toString()
                    txtMovieName.text = movie.title
                    txtReleaseDate.text = movie.release_date
                    txtRuntime.text = movie.runtime.getRuntimeText()
                    with(rvGenreList) {
                        adapter = GenreListAdapter {}.apply {
                            submitList(movie.genres)
                        }
                        layoutManager = LinearLayoutManager(requireContext(),
                            LinearLayoutManager.HORIZONTAL,
                            false)
                    }
                    txtMovieOverview.text = movie.overview
                }
            }
        }
        movieViewModel.toastLiveData.observe(viewLifecycleOwner, ::toast)
    }

    companion object {
        const val MOVIE_ID_KEY = "movie id key"
    }
}