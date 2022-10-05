package com.example.movieapp.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.movieapp.model.Genre
import com.example.movieapp.repository.GenreRepository
import com.example.movieapp.utils.SingleLiveEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class GenreListViewModel @Inject constructor(
    private val repository: GenreRepository,
    application: Application,
) : AndroidViewModel(application) {

    private val exceptionScope = CoroutineExceptionHandler { coroutineContext, throwable ->
        toastMutableLiveData.postValue("on context $coroutineContext: ${throwable.message}")
    }

    private val genreListMutableLiveData = MutableLiveData<List<Genre>>()
    private val toastMutableLiveData = SingleLiveEvent<String>()

    val genreListLiveData: LiveData<List<Genre>>
        get() = genreListMutableLiveData
    val toastLiveData: LiveData<String>
        get() = toastMutableLiveData

    fun getGenreMovieList() {
        viewModelScope.launch(Dispatchers.IO + exceptionScope) {
            genreListMutableLiveData.postValue(repository.getGenreMovieList())
        }
    }

    fun getGenreTVList() {
        viewModelScope.launch(Dispatchers.IO + exceptionScope) {
            genreListMutableLiveData.postValue(repository.getGenreTVList())
        }
    }
}