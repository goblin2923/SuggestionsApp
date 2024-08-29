package com.example.suggestionsapp_v2.ui.screens

import com.example.suggestionsapp_v2.data.source.FormWithUsers

data class UiForms(
    val optionName:String,
)

data class MainScreenUiState(
    val isLoading: Boolean = false,
    val forms: List<FormWithUsers> = emptyList()
)