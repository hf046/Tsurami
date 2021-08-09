package com.example.tsurami

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class FeelingRepository {
    val allFeelings: Flow<List<Feeling>> = flow {
        val feelings = mutableListOf<Feeling>()
        for (i in 1..4) {
            feelings.add(Feeling())
        }
        emit(feelings)
    }
}

class Feeling() {
    override fun toString(): String {
        return "<Feeling>[:hash]:${this.hashCode()};"
    }
}