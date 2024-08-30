package com.example.suggestionsapp_v2.data.source.notes

import com.google.firebase.Timestamp
import com.google.firebase.database.getValue
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.tasks.await
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale


class NoteRepository {
    private val database = Firebase.database(dbRef)
    private val notesRef = database.getReference("notes")

    suspend fun addNote(note: Note) {
        notesRef.child(note.id).setValue(note).await()
    }

    suspend fun getNotes(): List<Note> {
        val snapshot = notesRef.get().await()
        val notes = mutableListOf<Note>()
        for (child in snapshot.children) {
            child.getValue<Note>()?.let { notes.add(it) }
        }
        return notes
    }

    suspend fun refreshNotes(): List<Note> {
        val snapshot = notesRef.get().await()
        val notes = mutableListOf<Note>()
        for (child in snapshot.children) {
            child.getValue<Note>()?.let {notes.add(it) }
        }
        return notes
    }

    suspend fun deleteNote(noteId: String){
        notesRef.child(noteId).removeValue().await()
    }

    fun generateNoteId(): String {
        return notesRef.push().key!!
    }

}

const val dbRef = "https://suggestionsappdatabase-default-rtdb.asia-southeast1.firebasedatabase.app/"