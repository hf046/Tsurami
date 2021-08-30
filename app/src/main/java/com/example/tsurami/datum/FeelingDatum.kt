package com.example.tsurami.datum

import androidx.room.Embedded
import androidx.room.Relation
import com.example.tsurami.db.entity.Comment
import com.example.tsurami.db.entity.Feeling
import com.example.tsurami.db.entity.Location

data class FeelingDatum(
    @Embedded
    val feeling: Feeling,

    @Relation(
        parentColumn = "location_id",
        entityColumn = "id"
    )
    val location: Location?,

    @Relation(
        parentColumn = "id",
        entityColumn = "feeling_id"
    )
    val comment: Comment?
)
