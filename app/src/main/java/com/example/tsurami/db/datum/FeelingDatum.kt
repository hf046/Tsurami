package com.example.tsurami.db.datum

import android.annotation.SuppressLint
import androidx.room.Embedded
import androidx.room.Relation
import com.example.tsurami.db.entity.Comment
import com.example.tsurami.db.entity.Feeling
import com.example.tsurami.db.entity.Location
import java.text.SimpleDateFormat

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
        val sdf = SimpleDateFormat("yyyy-MM-dd {HH:mm:ss}")
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
                "  :<Comment>\n" +
                "    [:description]\n" +
                "    :${comment?.content}\n" +
                "  ;\n" +
                ";"
    }
}
