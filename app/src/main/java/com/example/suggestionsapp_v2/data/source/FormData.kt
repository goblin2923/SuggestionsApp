package com.example.suggestionsapp_v2.data.source

import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "FormData")
data class FormData(
//    @PrimaryKey(autoGenerate = true) val fId: Int,

    @PrimaryKey (autoGenerate = true) val fId: Int,
    val optionName: String,
    val votes: Int,
    val color: Int
){

}
