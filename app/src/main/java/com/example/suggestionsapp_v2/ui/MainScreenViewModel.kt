package com.example.suggestionsapp_v2.ui

import androidx.compose.ui.graphics.Color
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.suggestionsapp_v2.data.source.DefaultFormRepository
import com.example.suggestionsapp_v2.data.source.FormData
import com.example.suggestionsapp_v2.data.source.local.ColorSet
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class MainPageViewModel(val formRepo: DefaultFormRepository = DefaultFormRepository()) : ViewModel() {

    private val _formDataState = MutableStateFlow<List<FormData>>(emptyList())
    init{
        viewModelScope.launch {
            _formDataState.update {
                formRepo.getAll()!!
            }
        }
    }

    val formState = _formDataState.asStateFlow()

    private fun getRandomColor(): Color {
        return ColorSet.random()
    }

    private fun addColor(formDataState: List<FormData>) {
        val withColors = formDataState.map { form ->
            MainScreenUiState(
                fId = form.fId,
                optionName = form.optionName,
                votes = form.votes,
                color = getRandomColor()
            )
        }
//        _formState.update {
//            withColors
//        }
    }
}

