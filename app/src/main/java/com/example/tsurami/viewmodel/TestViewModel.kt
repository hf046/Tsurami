package com.example.tsurami.viewmodel

import androidx.lifecycle.*
import com.example.tsurami.db.entity.Test
import com.example.tsurami.repository.TestRepository
import kotlinx.coroutines.launch
import java.lang.IllegalArgumentException

class TestViewModel(private val repository: TestRepository) : ViewModel() {
    val allTests: LiveData<List<Test>> = repository.allTests.asLiveData()

    fun insert(test: Test) = viewModelScope.launch {
        repository.insert(test)
    }
}

class TestViewModelFactory(private val repository: TestRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(TestViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return TestViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}