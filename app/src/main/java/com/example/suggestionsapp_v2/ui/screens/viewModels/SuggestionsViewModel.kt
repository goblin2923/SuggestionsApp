package com.example.suggestionsapp_v2.ui.screens.viewModels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.suggestionsapp_v2.SuggestionsApp
import com.example.suggestionsapp_v2.data.source.DefaultFormRepository
import com.example.suggestionsapp_v2.ui.screens.mainScreen.MainScreenUiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch


class SuggestionsViewModel(
    val formRepo: DefaultFormRepository = DefaultFormRepository()
) : ViewModel() {

    private val _uiState = MutableStateFlow(MainScreenUiState())
    val uiState: StateFlow<MainScreenUiState> = _uiState.asStateFlow()

    private val _formDataState = formRepo.getAllForms()
    val formState =
        _formDataState.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5_000), emptyList())

    init {
        refreshForms()
    }

    fun getVotes() = formRepo.getVotes()

    fun refreshForms() {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(isLoading = true)

            try {
                formState
                formRepo.refreshForms()
//                _formDataState = _formDataState.
//                formRepo.getAllForms().collect { formList ->
                    _uiState.value = _uiState.value.copy(
                        isLoading = false,
//                        forms = formList
                    )
//                }
            } catch (e: Exception) {
                _uiState.value = _uiState.value.copy(isLoading = false)
            }
        }
    }

    // Define ViewModel factory in a companion object
    companion object {
        val Factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val defaultRepo = (this[APPLICATION_KEY] as SuggestionsApp).defaultRepo
                SuggestionsViewModel(formRepo = defaultRepo)
            }
        }
    }
}

