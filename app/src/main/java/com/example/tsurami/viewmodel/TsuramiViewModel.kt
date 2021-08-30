package com.example.tsurami.viewmodel

import androidx.lifecycle.*
import com.example.tsurami.datum.FeelingDatum
import com.example.tsurami.db.entity.Feeling
import com.example.tsurami.repository.TsuramiRepository
import kotlinx.coroutines.launch

class TsuramiViewModel(private val repository: TsuramiRepository): ViewModel() {
    val allFeelingDatum: LiveData<List<FeelingDatum>> = repository.allFeelings.asLiveData()

    fun insert(feeling: Feeling) = viewModelScope.launch {
        repository.insert(feeling)
    }

    fun insert(feelingDatum: FeelingDatum) = viewModelScope.launch {
        repository.insert(feelingDatum)
    }
}

class TsuramiViewModelFactory(private val repository: TsuramiRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(TsuramiViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return TsuramiViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}