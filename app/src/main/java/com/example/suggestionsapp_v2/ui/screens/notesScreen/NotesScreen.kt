package com.example.suggestionsapp_v2.ui.screens.notesScreen

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.suggestionsapp_v2.ui.screens.viewModels.NotesViewModel

@Composable
fun NotesScreen(viewModel: NotesViewModel = viewModel(factory = NotesViewModel.Factory)) {
    val notes by viewModel.notes.collectAsState()
    var title by remember { mutableStateOf("") }
    var content by remember { mutableStateOf("") }

    Column {
        OutlinedTextField(
            value = title,
            onValueChange = { title = it },
            label = { Text("Title") }
        )
        OutlinedTextField(
            value = content,
            onValueChange = { content = it },
            label = { Text("Content") },
            modifier = Modifier.height(150.dp)
        )

        Button(onClick = {
            viewModel.addNote(title, content)
            // Clear input fields after adding note
            title = ""
            content = ""
        }) {
            Text("Add Note")
        }

        // Display existing notes
        LazyColumn {
            items(notes) { note ->
                Text(text = note.content)
            }
        }
    }
}
