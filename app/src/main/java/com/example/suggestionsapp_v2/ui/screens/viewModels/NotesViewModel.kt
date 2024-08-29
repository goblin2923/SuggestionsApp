package com.example.suggestionsapp_v2.ui.screens.viewModels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.suggestionsapp_v2.data.source.notes.Note
import com.example.suggestionsapp_v2.data.source.notes.NoteRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class NotesViewModel(private val noteRepository: NoteRepository = NoteRepository()) : ViewModel() {
    private val _notes = MutableStateFlow<List<Note>>(emptyList())
    val notes: StateFlow<List<Note>> = _notes.asStateFlow()

    init {
        viewModelScope.launch {
            _notes.value = noteRepository.getNotes()
        }
    }

    // Add functions for adding, updating, and deleting notes as needed
}