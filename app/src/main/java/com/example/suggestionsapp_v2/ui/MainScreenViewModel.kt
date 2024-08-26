package com.example.suggestionsapp_v2.ui

import androidx.compose.ui.graphics.Color
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.createSavedStateHandle
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.CreationExtras
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.suggestionsapp_v2.SuggestionsApp
import com.example.suggestionsapp_v2.data.source.DefaultFormRepository
import com.example.suggestionsapp_v2.data.source.FormData
import com.example.suggestionsapp_v2.data.source.local.ColorSet
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class MainScreenViewModel(
    val formRepo: DefaultFormRepository = DefaultFormRepository(),
    private val savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val _formDataState = MutableStateFlow<List<FormData>>(emptyList())
    val formDataState: StateFlow<List<FormData>> = _formDataState.asStateFlow()


    init {
        viewModelScope.launch {
            formRepo.refreshForms()
            _formDataState.update {
                formRepo.getAllForms()!!
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
    }


    // Define ViewModel factory in a companion object
    companion object {
        val Factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val savedStateHandle = createSavedStateHandle()
                val defaultRepo = (this[APPLICATION_KEY] as SuggestionsApp).defaultRepo
                MainScreenViewModel(
                    formRepo = defaultRepo,
                    savedStateHandle = savedStateHandle
                )
            }
        }
    }
}

