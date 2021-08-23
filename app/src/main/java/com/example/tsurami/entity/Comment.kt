package com.example.tsurami.entity

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import java.io.Serializable
import java.util.*

@Entity(
    tableName = "comment"
    ,
    foreignKeys = [
        ForeignKey(
            entity = Feeling::class,
            parentColumns = ["id"],
            childColumns = ["feeling_id"],
            onDelete = ForeignKey.CASCADE
        )
    ]
)
data class Comment(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val feeling_id: Int,
//    val feeling: Feeling,

//    dates
    val create_date: Date,
    val update_date: Date,
//    content
    val content: String
): Serializable