package com.example.tsurami.db.entity

import androidx.room.*
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
    ],
    indices = [
        Index(
            value = ["feeling_id"],
            unique = true
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