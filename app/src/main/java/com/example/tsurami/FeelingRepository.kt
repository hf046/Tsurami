package com.example.tsurami

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class FeelingRepository {
    val allFeelings: Flow<List<Feeling>> = flow {
        val feelings = listOf(
            Feeling("a"),
            Feeling("b"),
            Feeling("c"),
            Feeling("d")
        )
        emit(feelings)
    }
}

class Feeling(val name: String) {
    override fun toString(): String {
        return "<Feeling>[:hash:name]:${this.hashCode()}:${name};"
    }
}