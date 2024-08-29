package com.example.suggestionsapp_v2.ui.screens.SuggestionOptionScreen

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.suggestionsapp_v2.data.source.FormData
import com.example.suggestionsapp_v2.ui.components.BaseRow
import com.example.suggestionsapp_v2.ui.components.FormOptionDivider
import com.example.suggestionsapp_v2.ui.screens.mainScreen.TAG
import com.example.suggestionsapp_v2.ui.screens.viewModels.SuggestionsViewModel

private const val TAG_S = "SuggestionOptionScreen"

@Composable
fun FormOptionScreen(
    option: FormData.Options,
    suggestionsViewModel: SuggestionsViewModel = viewModel(factory = SuggestionsViewModel.Factory)
) {
//    val uiState by suggestionsViewModel.uiState.collectAsState()
    val forms = suggestionsViewModel.formState.collectAsState()
    Log.w(TAG, "FormOptionScreen: option is $option")

    val filteredForms = forms.value.filter { it.formData.optionName == option }
//    val filteredForms = uiState.forms.filter { it.formData.optionName == option }
    Column(
        modifier = Modifier
            .padding(horizontal = 2.dp)
            .background(MaterialTheme.colorScheme.background),
    ) {
        Column(
            Modifier
                .weight(0.2f)
                .fillMaxWidth()
                .background(MaterialTheme.colorScheme.surface)
        ) {
            PrintFormOptionText(option = option.toString())
        }
        Column(Modifier.weight(0.8f)) {

            LazyColumn(
//            modifier = Modifier.clip(RoundedCornerShape(5))
            ) {
                items(filteredForms) { formWithUsers ->
                    val color = formWithUsers.formData.color!!
                    formWithUsers.users.forEach { formData ->
                        BaseRow(
                            color = Color(color),
                            name = formData.name,
                            suggestions = formData.suggestion!!,
                            time = formData.time!!
                        )
                    }
                }
            }
        }
    }
}


@Composable
fun PrintFormOptionText(option: String, modifier: Modifier = Modifier) {
    Column(
        modifier = modifier.padding(24.dp),
    ) {
        Text(
            text = "Option: ",
            color = MaterialTheme.colorScheme.onSurface,
            style = MaterialTheme.typography.bodyLarge,
        )
        Text(
            text = option,
            color = MaterialTheme.colorScheme.onPrimaryContainer,
            style = MaterialTheme.typography.displayLarge,
            modifier = Modifier
                .padding(start = 12.dp)
                .fillMaxWidth()
                .align(Alignment.CenterHorizontally)
        )
        FormOptionDivider(color = MaterialTheme.colorScheme.outline)
    }
}


@Preview(showBackground = false)
@Composable
fun prpep() {
    PrintFormOptionText(option = "painting")
}