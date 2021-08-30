package com.example.tsurami.db.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable

@Entity(tableName = "test")
data class Test(
    @PrimaryKey(
        autoGenerate = true
    )
    val id: Int,
    val text: String
): Serializable