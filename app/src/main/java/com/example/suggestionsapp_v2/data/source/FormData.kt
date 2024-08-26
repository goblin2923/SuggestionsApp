package com.example.suggestionsapp_v2.data.source

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "FormData")
data class FormData(
//    @PrimaryKey (autoGenerate = true) val fId: Int = 0,
    @PrimaryKey val optionName: Options,
    @ColumnInfo(name = "votes") val votes: Int = 0,
    @ColumnInfo(name = "color") val color: Int? = 0
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
//
//fun FormData.calculateVotes():Int{
//
//}
//
