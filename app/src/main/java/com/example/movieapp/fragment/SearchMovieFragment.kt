package com.example.movieapp.fragment

import android.os.Bundle
import android.view.View
import com.example.movieapp.databinding.FragmentSearchBinding

class SearchMovieFragment : BaseFragment<FragmentSearchBinding>(FragmentSearchBinding::inflate) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initUI()
        val mSearchView = binding.svMovieTv
        mSearchView.isFocusable = true
        mSearchView.isIconified = false
        mSearchView.requestFocusFromTouch()

    }

    private fun initUI() {
        binding.toolbar.setNavigationOnClickListener {
            activity?.onBackPressed()
        }
    }

}