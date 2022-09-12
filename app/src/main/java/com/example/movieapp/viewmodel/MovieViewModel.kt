package com.example.movieapp.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.movieapp.model.Cast
import com.example.movieapp.model.Movie
import com.example.movieapp.repository.MovieRepository
import com.example.movieapp.utils.SingleLiveEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MovieViewModel @Inject constructor(
    private val repository: MovieRepository,
    application: Application,
) : AndroidViewModel(application) {

    private val exceptionScope = CoroutineExceptionHandler { coroutineContext, throwable ->
        toastMutableLiveData.postValue("Error: ${throwable.message} on context: $coroutineContext ")
    }

    private val movieMutableLiveData = MutableLiveData<Movie?>()
    private val castListMutableLiveData = MutableLiveData<List<Cast>>()
    private val toastMutableLiveData = SingleLiveEvent<String>()

    val movieLiveData: LiveData<Movie?>
        get() = movieMutableLiveData

    val toastLiveData: LiveData<String>
        get() = toastMutableLiveData

    val castListLiveData: LiveData<List<Cast>>
        get() = castListMutableLiveData

    fun getMovie(movieId: Long) {
        viewModelScope.launch(exceptionScope + Dispatchers.IO) {
            movieMutableLiveData.postValue(repository.getMovieById(movieId))
        }
    }

    fun getCastOfMovie(movieId: Long) {
        viewModelScope.launch(exceptionScope + Dispatchers.IO) {
            castListMutableLiveData.postValue(repository.getCastOfMovie(movieId))
        }
    }
}