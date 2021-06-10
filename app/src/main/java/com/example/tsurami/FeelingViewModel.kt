package com.example.tsurami

import android.util.Log
import androidx.lifecycle.*
import kotlinx.coroutines.launch
import java.lang.IllegalArgumentException

class FeelingViewModel(private val repository: FeelingRepository) : ViewModel() {
    val allFeelings: LiveData<List<Feeling>> = repository.allFeelings.asLiveData()
}

class FeelingViewModelFactory(private val repository: FeelingRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        Log.d("test", "FeelingViewModelFactory create()")
        if (modelClass.isAssignableFrom(FeelingViewModel::class.java)) {
            return FeelingViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}