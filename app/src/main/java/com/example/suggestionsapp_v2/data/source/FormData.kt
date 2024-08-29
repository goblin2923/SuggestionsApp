package com.example.suggestionsapp_v2.data.source

import androidx.room.ColumnInfo
import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import androidx.room.Relation

data class FormWithUsers(
    @Embedded val formData: FormData,
    @Relation(
        parentColumn = "optionName",
        entityColumn = "formOptionName"
    )
    val users: List<UserData>
)

@Entity(tableName = "FormData")
data class FormData(
    @PrimaryKey val optionName: Options,
    @ColumnInfo(name = "votes") val votes: Int = 0,
    @ColumnInfo(name = "color") val color: Int? = 0,
//    @ColumnInfo(name = "userInfo") val userData: UserData,
){
    enum class Options{
        PAINTING,
        SKETCHING,
        MIME,
        QAWALI,
        FASHION,
        NULL
    }
}

@Entity(
    tableName = "UserData",
    foreignKeys = [ForeignKey(
        entity = FormData::class,
        parentColumns = ["optionName"],
        childColumns = ["formOptionName"],
        onDelete = ForeignKey.CASCADE
    )]
)
data class UserData(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    @ColumnInfo(name = "formOptionName") val formOptionName: FormData.Options,
    @ColumnInfo(name = "name") val name: String,
    @ColumnInfo(name = "suggestion") val suggestion: String? = "",
    @ColumnInfo(name = "time") val time: String? = ""
)
