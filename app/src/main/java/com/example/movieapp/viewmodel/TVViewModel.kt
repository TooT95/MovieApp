package com.example.movieapp.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.movieapp.model.Cast
import com.example.movieapp.model.TV
import com.example.movieapp.repository.TVRepository
import com.example.movieapp.utils.SingleLiveEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class TVViewModel @Inject constructor(
    private val repository: TVRepository,
    application: Application,
) : AndroidViewModel(application) {

    private val exceptionScope = CoroutineExceptionHandler { coroutineContext, throwable ->
        Timber.d("Error: ${throwable.message} on context: $coroutineContext ")
        toastMutableLiveData.postValue("Error: ${throwable.message} on context: $coroutineContext ")
    }

    private val tvMutableLiveData = MutableLiveData<TV?>()
    private val castListMutableLiveData = MutableLiveData<List<Cast>>()
    private val toastMutableLiveData = SingleLiveEvent<String>()

    val tvLiveData: LiveData<TV?>
        get() = tvMutableLiveData

    val toastLiveData: LiveData<String>
        get() = toastMutableLiveData

    val castListLiveData: LiveData<List<Cast>>
        get() = castListMutableLiveData

    fun getTV(tvId: Long) {
        viewModelScope.launch(exceptionScope + Dispatchers.IO) {
            tvMutableLiveData.postValue(repository.getTVById(tvId))
        }
    }

    fun getCastOfTV(tvId: Long) {
        viewModelScope.launch(exceptionScope + Dispatchers.IO) {
            castListMutableLiveData.postValue(repository.getCastOfTv(tvId))
        }
    }
}