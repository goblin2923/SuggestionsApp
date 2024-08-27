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
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.forEach
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class MainScreenViewModel(
    val formRepo: DefaultFormRepository = DefaultFormRepository()
) : ViewModel() {


    private val _formDataState = formRepo.getAllForms()
    val formState =
        _formDataState.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5_000), emptyList())

    init {

        viewModelScope.launch {
            formRepo.refreshForms()
        }
    }

    // Define ViewModel factory in a companion object
    companion object {
        val Factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val defaultRepo = (this[APPLICATION_KEY] as SuggestionsApp).defaultRepo
                MainScreenViewModel(formRepo = defaultRepo)
            }
        }
    }

    fun getVotes() = formRepo.getVotes()
}

