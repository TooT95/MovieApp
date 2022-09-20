package com.example.movieapp.fragment

import android.os.Bundle
import android.view.View
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.movieapp.adapter.CastListAdapter
import com.example.movieapp.adapter.GenreListAdapter
import com.example.movieapp.databinding.FragmentMovieBinding
import com.example.movieapp.extensions.getPathWithBaseUrl
import com.example.movieapp.extensions.getRuntimeText
import com.example.movieapp.extensions.glideImage
import com.example.movieapp.model.Cast
import com.example.movieapp.model.Movie
import com.example.movieapp.viewmodel.MovieViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MovieFragment : BaseFragment<FragmentMovieBinding>(FragmentMovieBinding::inflate) {

    private val movieViewModel: MovieViewModel by viewModels()
    private val castListAdapter: CastListAdapter by lazy {
        CastListAdapter()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            val movieId = it.getLong(MOVIE_ID_KEY)
            movieViewModel.getMovie(movieId)
            movieViewModel.getCastOfMovie(movieId)
        }

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initUI()
        observeViewModel()
    }

    private fun initUI() {
        showPbMovie(true)
        showPbCastList(true)
        binding.toolbar.setNavigationOnClickListener {
            activity?.onBackPressed()
        }
        binding.rvCastList.apply {
            adapter = castListAdapter
            setHasFixedSize(true)
            layoutManager =
                LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        }
    }

    private fun observeViewModel() {
        movieViewModel.movieLiveData.observe(viewLifecycleOwner, ::initMovieUI)
        movieViewModel.toastLiveData.observe(viewLifecycleOwner, ::toast)
        movieViewModel.castListLiveData.observe(viewLifecycleOwner, ::initCastList)
    }

    private fun initMovieUI(it: Movie?) {
        it?.let { movie ->
            binding.apply {
                showPbMovie(false)
                ivMovieIcon.glideImage(requireContext(),
                    movie.backdrop_path?.getPathWithBaseUrl() ?: "")
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
                txtTvOverview.text = movie.overview
            }
        }
    }

    private fun initCastList(castList: List<Cast>) {
        showPbCastList(false)
        castListAdapter.submitList(castList)
    }

    companion object {
        const val MOVIE_ID_KEY = "movie id key"
    }

    private fun showPbMovie(show: Boolean) {
        binding.pbMovie.isVisible = show
    }

    private fun showPbCastList(show: Boolean) {
        binding.apply {
            pbCastList.isVisible = show
            rvCastList.isVisible = !show
        }
    }

}