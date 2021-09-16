package com.example.tsurami.viewmodel

import androidx.lifecycle.*
import com.example.tsurami.db.datum.FeelingDatum
import com.example.tsurami.db.entity.Comment
import com.example.tsurami.db.entity.Feeling
import com.example.tsurami.db.entity.Location
import com.example.tsurami.repository.TsuramiRepository
import kotlinx.coroutines.launch

class FeelingDatumViewModel(private val repository: TsuramiRepository): ViewModel() {
    val allFeelingData: LiveData<List<FeelingDatum>> = repository.allWords.asLiveData()

    fun insert(feeling: Feeling, location: Location?, comment: Comment?) = viewModelScope.launch {
        repository.insert(feeling, location, comment)
    }
}

class FeelingDatumViewModelFactory(private val repository: TsuramiRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(FeelingDatumViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return FeelingDatumViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}