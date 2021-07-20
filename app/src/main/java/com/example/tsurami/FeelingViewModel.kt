package com.example.tsurami

import android.util.Log
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
        Timber.d("create()")
        if (modelClass.isAssignableFrom(FeelingViewModel::class.java)) {
            return FeelingViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}