package com.example.tsurami

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.asLiveData
import timber.log.Timber
import java.lang.IllegalArgumentException

class FeelingViewModel(private val repository: FeelingRepository) : ViewModel() {
    val allFeelings: LiveData<List<Feeling>> = repository.allFeelings.asLiveData()
}

class FeelingViewModelFactory(private val repository: FeelingRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        Timber.d("\\[:create]")
        Timber.d("\\:$this")
        if (modelClass.isAssignableFrom(FeelingViewModel::class.java)) {
            Timber.d("\\:<<make FeelingViewModel>>")
            Timber.d("\\:$this")
            Timber.d(";")
            @Suppress("UNCHECKED_CAST")
            return FeelingViewModel(repository) as T
        }
        Timber.d("\\:$this")
        Timber.d(";")
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}