package com.example.movieapp.fragment

import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.widget.SearchView
import androidx.appcompat.view.menu.MenuBuilder
import androidx.core.view.MenuHost
import androidx.core.view.MenuProvider
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
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
            genreListAdapter.submitList(emptyList())
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
        binding.inToolbar.toolbar.apply {
            setNavigationIcon(R.drawable.ic_menu)
            inflateMenu(R.menu.menu_main)
            setOnMenuItemClickListener { menuItem ->
                if (menuItem.itemId == R.id.item_search) {
                    findNavController().navigate(R.id.action_mainFragment_to_searchMovieFragment)
                }
                return@setOnMenuItemClickListener false
            }
//            val searchView = (menu.findItem(R.id.item_search).actionView as SearchView)
//            searchView.setOnQueryTextListener(object :
//                SearchView.OnQueryTextListener {
//                override fun onQueryTextSubmit(text: String?): Boolean {
//                    Timber.d("onQueryTextSubmit $text")
//                    return false
//                }
//
//                override fun onQueryTextChange(text: String?): Boolean {
//                    Timber.d("onQueryTextChange $text")
//                    return false
//                }
//
//            })
//            searchView.setOnCloseListener {
//                Timber.d("setOnCloseListener")
//                return@setOnCloseListener false
//            }
//            searchView.setOnSearchClickListener {
//                Timber.d("setOnSearchClickListener")
//            }
        }
        initGenreLIst()
        initDiscoverLIst()
        initDiscoverList()
    }

    private fun menuItemSelected(menuItem: MenuItem) {
        toast("item clicked ${menuItem.itemId}")
        when (menuItem.itemId) {
            R.id.item_search -> {
                val searchView = menuItem.actionView as SearchView
                searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
                    override fun onQueryTextSubmit(text: String?): Boolean {
                        Timber.d("onQueryTextSubmit $text")
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
        val genre = genreListAdapter.currentList[indexBySelected]
        if (currentDiscoverList.isMovie())
            movieViewModel.getPopularMovieList(genre.id)
        else
            tvViewModel.getPopularTVList(genre.id)
        initDiscoverList()
    }
}