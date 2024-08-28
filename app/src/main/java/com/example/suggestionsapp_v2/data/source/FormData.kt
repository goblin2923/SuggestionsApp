package com.example.suggestionsapp_v2.data.source

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "FormData")
data class FormData(
//    @PrimaryKey (autoGenerate = true) val fId: Int = 0,
    @PrimaryKey val optionName: Options,
    @ColumnInfo(name = "votes") val votes: Int = 0,
    @ColumnInfo(name = "color") val color: Int? = 0,
    @ColumnInfo(name = "name") val name: String = "",
    @ColumnInfo(name = "suggestion") val suggestion: String? = "",
    @ColumnInfo(name = "time") val time: String = ""
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