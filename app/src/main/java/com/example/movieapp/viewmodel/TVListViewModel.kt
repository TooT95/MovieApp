package com.example.movieapp.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.movieapp.model.TV
import com.example.movieapp.repository.TVRepository
import com.example.movieapp.utils.SingleLiveEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class TVListViewModel @Inject constructor(
    private val repository: TVRepository,
    application: Application,
) : AndroidViewModel(application) {

    private val exceptionScope = CoroutineExceptionHandler { coroutineContext, throwable ->
        toastMutableLiveData.postValue("on context $coroutineContext: ${throwable.message}")
    }

    private val tvListMutableLiveData = MutableLiveData<List<TV>>()
    private val toastMutableLiveData = SingleLiveEvent<String>()

    val tvListLiveData: LiveData<List<TV>>
        get() = tvListMutableLiveData
    val toastLiveData: LiveData<String>
        get() = toastMutableLiveData

    fun getPopularTVList(genreId:Int?) {
        viewModelScope.launch {
            withContext(Dispatchers.IO + exceptionScope) {
                tvListMutableLiveData.postValue(repository.getPopularTVList(genreId))
            }
        }
    }

}