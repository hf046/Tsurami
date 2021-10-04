package com.example.tsurami.db.datum

import android.annotation.SuppressLint
import androidx.room.Embedded
import androidx.room.Relation
import com.example.tsurami.db.entity.Comment
import com.example.tsurami.db.entity.Feeling
import com.example.tsurami.db.entity.Location
import com.example.tsurami.db.entity.converter.Converter
import java.io.Serializable
import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter

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
    val comments: List<Comment>
) : Serializable {
    @SuppressLint("SimpleDateFormat")
    override fun toString(): String {
        var commentsStr = ""
        comments.forEach {
            commentsStr += it
        }

        val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd' {'HH:mm:ss'}'")

        return ":<FeelingDatum>\n" +
                "  [:feeling :location :comments]\n" +
                "  :<Feeling>[:datetime :a :b]\n" +
                "    :${feeling.createDate.format(formatter)}\n" +
                "    :${feeling.mentalParamA}\n" +
                "    :${feeling.mentalParamB}\n" +
                "  ;\n" +
                "  :<Location>[:latitude :longitude]\n" +
                "    :${location?.latitude}\n" +
                "    :${location?.longitude}\n" +
                "  ;\n" +
                "  :List<Comment>\n" +
                "    :\n" +
                "$commentsStr" +
                ";"
    }

    companion object {
        fun createFeelingDatum(a: Int, b: Int, location: android.location.Location?, vararg comments: String): FeelingDatum {
            val converter = Converter()

            val date = ZonedDateTime.now()

            var locationEntity: Location? = null
            if (location != null) locationEntity = converter.convAppLoc2ELoc(location)

            val feeling = Feeling(0, locationEntity?.id, date, date, a, b)

            val commentEntities: MutableList<Comment> = mutableListOf()
            comments.forEach {
                commentEntities.add(Comment(0, feeling.id, date, date, it))
            }

            return FeelingDatum(
                feeling,
                locationEntity,
                commentEntities
            )
        }
    }
}
