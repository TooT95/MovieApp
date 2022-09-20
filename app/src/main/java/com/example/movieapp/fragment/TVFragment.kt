package com.example.movieapp.fragment

import android.os.Bundle
import android.view.View
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.movieapp.adapter.CastListAdapter
import com.example.movieapp.adapter.GenreListAdapter
import com.example.movieapp.databinding.FragmentTvBinding
import com.example.movieapp.extensions.getPathWithBaseUrl
import com.example.movieapp.extensions.glideImage
import com.example.movieapp.model.Cast
import com.example.movieapp.model.TV
import com.example.movieapp.viewmodel.TVViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class TVFragment : BaseFragment<FragmentTvBinding>(FragmentTvBinding::inflate) {

    private val tvViewModel: TVViewModel by viewModels()
    private val castListAdapter: CastListAdapter by lazy {
        CastListAdapter()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            val tVId = it.getLong(TV_ID_KEY)
            tvViewModel.getTV(tVId)
            tvViewModel.getCastOfTV(tVId)
        }

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initUI()
        observeViewModel()
    }

    private fun initUI() {
        showPbTV(true)
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
        tvViewModel.tvLiveData.observe(viewLifecycleOwner, ::initTVUI)
        tvViewModel.toastLiveData.observe(viewLifecycleOwner, ::toast)
        tvViewModel.castListLiveData.observe(viewLifecycleOwner, ::initCastList)
    }

    private fun initTVUI(it: TV?) {
        it?.let { tv ->
            binding.apply {
                showPbTV(false)
                ivTvIcon.glideImage(requireContext(),
                    tv.backdrop_path?.getPathWithBaseUrl() ?: "")
                txtVoteAverage.text = tv.vote_average.toString()
                txtVoteCount.text = tv.vote_count.toString()
                txtTvName.text = tv.name
                txtReleaseDate.text = tv.first_air_date
                txtRuntime.text = tv.episode_run_time?.joinToString(",")
                with(rvGenreList) {
                    adapter = GenreListAdapter {}.apply {
                        submitList(tv.genres)
                    }
                    layoutManager = LinearLayoutManager(requireContext(),
                        LinearLayoutManager.HORIZONTAL,
                        false)
                }
                txtTvOverview.text = tv.overview
            }
        }
    }

    private fun initCastList(castList: List<Cast>) {
        showPbCastList(false)
        castListAdapter.submitList(castList)
    }

    companion object {
        const val TV_ID_KEY = "tv id key"
    }

    private fun showPbTV(show: Boolean) {
        binding.pbTv.isVisible = show
    }

    private fun showPbCastList(show: Boolean) {
        binding.apply {
            pbCastList.isVisible = show
            rvCastList.isVisible = !show
        }
    }
}