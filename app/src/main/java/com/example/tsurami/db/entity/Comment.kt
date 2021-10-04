package com.example.tsurami.db.entity

import androidx.room.*
import java.io.Serializable
import java.time.ZonedDateTime

@Entity(
    tableName = "comment",
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
    @ColumnInfo(name = "id")
    val id: Int,
    @ColumnInfo(name = "feeling_id")
    var feelingId: Int,

//    dates
    @ColumnInfo(name = "create_date")
    val createDate: ZonedDateTime,
    @ColumnInfo(name = "update_date")
    val updateDate: ZonedDateTime,
//    content
    @ColumnInfo(name = "content")
    val content: String
): Serializable {
    override fun toString(): String {
//        本当はインデントを調整させたい。
        return "<Comment>[:content]\n" +
                "  :$content\n" +
                ";\n"
    }
}