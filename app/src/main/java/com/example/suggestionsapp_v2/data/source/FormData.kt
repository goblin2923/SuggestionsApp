package com.example.suggestionsapp_v2.data.source

import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "FormData")
data class FormData(
    @PrimaryKey (autoGenerate = true) val fId: Int = 0,
    val optionName: String,
    val votes: Int = 0,
    val color: Int? = 0
){
    enum class Options{

    }
}


