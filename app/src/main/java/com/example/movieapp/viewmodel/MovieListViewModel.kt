package com.example.movieapp.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.movieapp.model.Movie
import com.example.movieapp.repository.MovieRepository
import com.example.movieapp.utils.SingleLiveEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class MovieListViewModel @Inject constructor(
    private val repository: MovieRepository,
    application: Application,
) : AndroidViewModel(application) {

    private val exceptionScope = CoroutineExceptionHandler { coroutineContext, throwable ->
        toastMutableLiveData.postValue("on context $coroutineContext: ${throwable.message}")
    }

    private val movieListMutableLiveData = MutableLiveData<List<Movie>>()
    private val toastMutableLiveData = SingleLiveEvent<String>()

    val movieListLiveData: LiveData<List<Movie>>
        get() = movieListMutableLiveData
    val toastLiveData: LiveData<String>
        get() = toastMutableLiveData

    fun getPopularMovieList(genreId: Int?) {
        viewModelScope.launch {
            withContext(Dispatchers.IO + exceptionScope) {
                movieListMutableLiveData.postValue(repository.getPopularMovieList(genreId))
            }
        }
    }

    fun getMovieListByQueryText(text: String) {
        viewModelScope.launch {
            withContext(Dispatchers.IO + exceptionScope) {
                movieListMutableLiveData.postValue(repository.getMovieByQueryText(text))
            }
        }
    }

}