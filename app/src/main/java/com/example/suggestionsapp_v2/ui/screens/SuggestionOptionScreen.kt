package com.example.suggestionsapp_v2.ui.screens

import android.util.Log
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.suggestionsapp_v2.data.source.FormData

private const val TAG_S = "SuggestionOptionScreen"

@Composable
fun FormOptionScreen(
    option: FormData.Options,
    suggestionsViewModel: SuggestionsViewModel = viewModel(factory = SuggestionsViewModel.Factory)
) {
    val uiState by suggestionsViewModel.uiState.collectAsState()
//    val forms = suggestionsViewModel.formState.collectAsState()
    Log.w(TAG, "FormOptionScreen: option is $option", )

    val filteredForms = uiState.forms.filter {it.formData.optionName == option }
    Log.w(TAG, "FormOptionScreen: : $filteredForms", )

//    LazyColumn {
//        items(filteredForms) {  ->
//            Text(text = "Name: ${formData.}, Time: ${formData.time}, Suggestion: ${formData.suggestion}")
//        }
//    }
}



@Preview
@Composable
fun previ(){

}