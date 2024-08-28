package com.example.suggestionsapp_v2.ui.mainScreen

import com.example.suggestionsapp_v2.data.source.FormData

data class UiForms(
    val optionName:String,
)

data class MainScreenUiState(
    val isLoading: Boolean = false,
    val forms: List<FormData> = emptyList()
)