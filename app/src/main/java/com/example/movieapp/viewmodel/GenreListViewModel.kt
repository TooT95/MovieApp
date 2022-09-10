package com.example.movieapp.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.movieapp.model.Genre
import com.example.movieapp.repository.GenreRepository
import com.example.movieapp.utils.SingleLiveEvent
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.lang.Exception

class GenreListViewModel(application: Application) : AndroidViewModel(application) {

    private val exceptionScope = CoroutineExceptionHandler { coroutineContext, throwable ->
        toastMutableLiveData.postValue("on context $coroutineContext: ${throwable.message}")
    }

    private val repository = GenreRepository()

    private val genreListMutableLiveData = MutableLiveData<List<Genre>>()
    private val toastMutableLiveData = SingleLiveEvent<String>()

    val genreListLiveData: LiveData<List<Genre>>
        get() = genreListMutableLiveData
    val toastLiveData: LiveData<String>
        get() = toastMutableLiveData

    fun getGenreList() {
        viewModelScope.launch {
            withContext(Dispatchers.IO + exceptionScope) {
                genreListMutableLiveData.postValue(repository.getGenreList())
            }
        }
    }
}