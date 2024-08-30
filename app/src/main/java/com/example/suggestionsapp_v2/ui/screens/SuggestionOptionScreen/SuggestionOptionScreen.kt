package com.example.suggestionsapp_v2.ui.screens.SuggestionOptionScreen

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowLeft
import androidx.compose.material3.Icon
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
import com.example.suggestionsapp_v2.data.source.FormWithUsers
import com.example.suggestionsapp_v2.ui.components.BaseRow
import com.example.suggestionsapp_v2.ui.components.FormOptionDivider
import com.example.suggestionsapp_v2.ui.components.GoBack
import com.example.suggestionsapp_v2.ui.screens.mainScreen.TAG
import com.example.suggestionsapp_v2.ui.screens.viewModels.SuggestionsViewModel

private const val TAG_S = "SuggestionOptionScreen"

@Composable
fun FormOptionScreen(
    option: FormData.Options,
    forms: List<FormWithUsers>,
    goBack: () -> Unit,
) {
//    val uiState by suggestionsViewModel.uiState.collectAsState()
//    val forms = suggestionsViewModel.formState.collectAsState()
    Log.w(TAG, "FormOptionScreen: option is $option")
//    Log.d(TAG, "FormOptionScreen: forms are ${forms.value}")


    val filteredForms = forms.filter { it.formData.optionName == option }
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

            PrintFormOptionText(option = option.toString(), goBack = goBack)
        }
        Column(Modifier.weight(0.8f)) {

            LazyColumn(
//            modifier = Modifier.clip(RoundedCornerShape(5))
            ) {
                Log.d(TAG, "FormOptionScreen() called, $filteredForms")
                items(filteredForms) { formWithUsers ->
                    val color = formWithUsers.formData.color!!

                    val displayedNames = mutableSetOf<String>()
                    formWithUsers.users.forEach { formData ->

                        if (formData.name !in displayedNames) {
                            BaseRow(
                                color = Color(color),
                                name = formData.name,
                                suggestions = formData.suggestion!!,
                                time = formData.time!!
                            )
                            displayedNames.add(formData.name)
                        }
                    }
                }
            }
        }
    }
}


@Composable
fun PrintFormOptionText(
    modifier: Modifier = Modifier,
    goBack: () -> Unit = {},
    option: String,
    ) {
    Column(
        modifier = modifier.padding(12.dp),
    ) {
        Text(
            text = "Option: ",
            color = MaterialTheme.colorScheme.onSurface,
            style = MaterialTheme.typography.bodyLarge,
            modifier = Modifier.padding(start = 36.dp)
        )
        Row(verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center)
        {
            Icon(
                Icons.AutoMirrored.Filled.KeyboardArrowLeft,
                contentDescription = "Go back",
                Modifier.clickable { goBack() }
                    .size(32.dp)
                ,
                tint = MaterialTheme.colorScheme.onSurface,
            )
            Text(
                text = option,
                color = MaterialTheme.colorScheme.onPrimaryContainer,
                style = MaterialTheme.typography.displayLarge,
                modifier = Modifier
                    .padding(start = 12.dp)
                    .fillMaxWidth()
            )
            FormOptionDivider(color = MaterialTheme.colorScheme.outline)
        }
    }
}


@Preview(showBackground = false)
@Composable
fun Prpep() {
    PrintFormOptionText(option = "painting")
}