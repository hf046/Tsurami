package com.example.tsurami.dao

import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.tsurami.entity.Comment

interface CommentDao {
    @Query(
        "SELECT " +
                "comment.create_date AS date, " +
                "comment.content AS content " +
                "FROM comment " +
                "WHERE id = :feeling_id " +
                "ORDER BY comment.create_date DESC"
    )
    fun getComments(feeling_id: Int): List<Comment>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insert(comment: Comment): Int
}