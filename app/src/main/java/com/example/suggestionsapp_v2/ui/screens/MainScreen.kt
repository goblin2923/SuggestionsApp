package com.example.suggestionsapp_v2.ui.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.suggestionsapp_v2.data.source.FormData
import com.example.suggestionsapp_v2.data.source.FormWithUsers
import com.example.suggestionsapp_v2.ui.components.AnimatedCircle
import com.example.suggestionsapp_v2.ui.components.BaseRow
import io.ktor.client.request.forms.formData

const val TAG = "easytosearch"

@Composable
fun DisplayMainScreen(
    modifier: Modifier = Modifier,
    uiState: MainScreenUiState,
    onAccountClick: (String) -> Unit
) {
//    val mainScreenViewModel: SuggestionsViewModel = viewModel(factory = SuggestionsViewModel.Factory),
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = DEFAULT_PADDING)
            .padding(top = DEFAULT_PADDING),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
//        val formDataState = mainScreenViewModel.formState.value.sortedByDescending { formData -> formData.formData.votes }
        val formDataState = uiState.forms.sortedByDescending { formData -> formData.formData.votes }
        val colorList: List<Color> = formDataState.map { formData ->
            Color(formData.formData.color ?: 0)
        }
        Column(
            modifier = modifier
                .weight(.6f)
                .fillMaxWidth()
                .padding(DEFAULT_PADDING),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
        ) {
            DisplayCircle(
                items = formDataState,
                votes = { formData -> formData.votes },
                colors = colorList,
                totalVotes = formDataState.sumOf { it.formData.votes },
            )
        }
        LazyColumn(
            modifier = modifier
                .weight(0.4f)
                .fillMaxWidth()
                .padding(DEFAULT_PADDING)
                .clip(RoundedCornerShape(7)),
            horizontalAlignment = Alignment.Start,
            verticalArrangement = Arrangement.Top
        ) {
            items(formDataState) { formData ->
                BaseRow(
                    color = Color(formData.formData.color ?: Color.Red.toArgb()),
                    title = formData.formData.optionName.toString().lowercase().replaceFirstChar {
                            if (it.isLowerCase()) it.titlecase(java.util.Locale.ENGLISH) else it.toString()
                        },
                    votes = formData.formData.votes,
                    modifier = Modifier.clickable { onAccountClick(formData.formData.optionName.toString()) },
                )

            }
        }
//        Spacer(modifier = Modifier.padding(bottom = 40.dp))
    }
}

@Composable
fun DisplayCircle(
//fun DisplayCircle(
    items: List<FormWithUsers>,
    colors: List<Color>,
    votes: (FormData) -> Int,
    totalVotes: Int,
    modifier: Modifier = Modifier,
) {
    Box(modifier = modifier) {
        AnimatedCircle(
            proportions = items.extractProportions { votes(it.formData) },
            colors = colors,
            Modifier
                .padding(DEFAULT_PADDING)
                .height(300.dp)
                .fillMaxSize()
        )
        Column(modifier = Modifier.align(Alignment.Center)) {
            var modifier = Modifier.align(Alignment.CenterHorizontally)
            Text(
                text = totalVotes.toString(),
//                style = MaterialTheme.typography.headlineLarge,
                modifier = modifier,
                fontSize = 72.sp,
                color = MaterialTheme.colorScheme.onPrimaryContainer
            )
            Text(
                text = "Votes", style = MaterialTheme.typography.titleLarge, modifier = modifier,
//                fontSize = 60.sp,
                color = MaterialTheme.colorScheme.onPrimaryContainer
            )
        }
    }
}



fun <E> List<E>.extractProportions(selector: (E) -> Int): List<Float> {
    val total = this.sumOf { selector(it).toDouble() }
    return this.map { (selector(it) / total).toFloat() }
}

val DEFAULT_PADDING = 16.dp