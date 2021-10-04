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

    fun save(feelingDatum: FeelingDatum) {
        val location = if (feelingDatum.location == null) null
            else Location(
            0,
            feelingDatum.location.time,
            feelingDatum.location.latitude,
            feelingDatum.location.longitude,
            feelingDatum.location.altitude,
            feelingDatum.location.accuracy
        )

        val feeling = Feeling(
            0,
            location?.id,
            feelingDatum.feeling.createDate,
            feelingDatum.feeling.updateDate,
            feelingDatum.feeling.mentalParamA,
            feelingDatum.feeling.mentalParamB
        )

        val comments = mutableListOf<Comment>()
        feelingDatum.comments.forEach {
            comments.add(Comment(
                0,
                feeling.id,
                it.createDate,
                it.updateDate,
                it.content
            ))
        }

        viewModelScope.launch {
            repository.insert(feeling, location, comments)
        }
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