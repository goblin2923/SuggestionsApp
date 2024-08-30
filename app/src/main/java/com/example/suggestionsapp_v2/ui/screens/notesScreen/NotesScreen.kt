package com.example.suggestionsapp_v2.ui.screens.notesScreen

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.suggestionsapp_v2.ui.components.BaseRow
import com.example.suggestionsapp_v2.ui.screens.viewModels.NotesViewModel

@Composable
fun NotesScreen(viewModel: NotesViewModel = viewModel(factory = NotesViewModel.Factory)) {
    val notes by viewModel.notes.collectAsState()
    var title by remember { mutableStateOf("") }
    var content by remember { mutableStateOf("") }

    Column(
        Modifier
            .padding(24.dp)
            .fillMaxWidth(), horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "New note? :)",
            modifier = Modifier
                .padding(16.dp)
                .align(Alignment.Start),
            color = MaterialTheme.colorScheme.onPrimaryContainer,
            fontSize = MaterialTheme.typography.titleLarge.fontSize
        )
        Box(
            modifier = Modifier.weight(0.3f), contentAlignment = Alignment.BottomEnd
        ) {
            var titleLabel = remember { mutableStateOf("Title") }
            var contentLabel = remember { mutableStateOf("Content")}

            Column() {
                OutlinedTextField(modifier = Modifier.fillMaxWidth(),
                    value = title,
                    onValueChange = { title = it },
                    label = { Text(text = titleLabel.value) })
                OutlinedTextField(value = content,
                    onValueChange = { content = it },
                    label = { Text(contentLabel.value) },
                    modifier = Modifier
                        .height(150.dp)
                        .fillMaxWidth()
                )
            }
            Button(
                onClick = {
                    if (title.isEmpty()) {
                        titleLabel.value = "Please add a title!"
                        return@Button
                    }
                    else if (content.isEmpty()){
                        contentLabel.value = "Please add some content!"
                        return@Button
                    }
                    else {
                        viewModel.addNote(title, content)

                        title = ""
                        content = ""
                        titleLabel.value = "Title"
                        contentLabel.value = "Content"
                    }
                }, modifier = Modifier.padding(16.dp)
            ) {
                Text("Add Note")
            }
        }
//        Spacer(modifier = Modifier.height(24.dp))
        Text(
            text = "Notes history :o ",
            modifier = Modifier
                .padding(16.dp)
                .align(Alignment.Start),
            color = MaterialTheme.colorScheme.onPrimaryContainer,
            fontSize = MaterialTheme.typography.titleLarge.fontSize
        )
        Column(modifier = Modifier
            .weight(0.7f)) {
            LazyColumn(
                modifier = Modifier
                    .clip(RoundedCornerShape(2)),

                ) {
                items(notes) { note ->
                    BaseRow(color = Color.White,
                        title = note.title,
                        content = note.content,
                        time = note.timestamp,
                        id = note.id,
                        onDelete = { note.id.let { viewModel.deleteNote(it) } })
                }
            }
        }

    }
}
