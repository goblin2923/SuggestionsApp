package com.example.suggestionsapp_v2.ui

import androidx.compose.ui.graphics.Color
import androidx.fragment.app.Fragment

data class MainScreenUiState(
    val fId: Int,
    val optionName: String,
    val votes: Int = 0,
    var color: Color
)