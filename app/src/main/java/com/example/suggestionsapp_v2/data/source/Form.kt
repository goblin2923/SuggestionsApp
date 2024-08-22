package com.example.suggestionsapp_v2.data.source

import androidx.compose.ui.graphics.Color

data class Form(
    val fId: String,
    val optionName: String,
    val votes: Int = 0,
    var color: Color = Color.Red
){

}
