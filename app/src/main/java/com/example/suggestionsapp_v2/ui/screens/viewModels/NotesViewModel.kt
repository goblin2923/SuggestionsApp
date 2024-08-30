package com.example.suggestionsapp_v2.ui.screens.viewModels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.suggestionsapp_v2.SuggestionsApp
import com.example.suggestionsapp_v2.data.source.notes.Note
import com.example.suggestionsapp_v2.data.source.notes.NoteRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class NotesViewModel(
    private val noteRepository: NoteRepository = NoteRepository()
) : ViewModel() {
    private val _notes = MutableStateFlow<List<Note>>(emptyList())
    val notes: StateFlow<List<Note>> = _notes.asStateFlow()

    init {
        refreshNotes()
    }

    fun refreshNotes() {
        viewModelScope.launch {
            _notes.value = noteRepository.refreshNotes()
        }
    }

    fun addNote(title: String, content: String) {
        viewModelScope.launch {
            val newNote =
                Note(id = noteRepository.generateNoteId(), title = title, content = content)
            noteRepository.addNote(newNote)

            _notes.value = noteRepository.getNotes()

//             _notes.value = _notes.value += newNote
        }
    }

    fun deleteNote(noteId: String) {
        viewModelScope.launch {
            noteRepository.deleteNote(noteId)
            _notes.value = noteRepository.refreshNotes()
        }
    }

    companion object {
        val Factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val application = (this[APPLICATION_KEY] as SuggestionsApp)
                val noteRepository = application.noteRepository
                NotesViewModel(noteRepository = noteRepository)
            }
        }
    }
}