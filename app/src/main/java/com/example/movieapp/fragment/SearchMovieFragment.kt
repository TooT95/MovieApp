package com.example.movieapp.fragment

import android.content.Context
import android.os.Bundle
import android.os.ResultReceiver
import android.view.View
import android.view.inputmethod.InputMethodManager
import androidx.core.content.ContextCompat.getSystemService
import com.example.movieapp.databinding.FragmentSearchBinding
import java.lang.reflect.InvocationTargetException
import java.lang.reflect.Method


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