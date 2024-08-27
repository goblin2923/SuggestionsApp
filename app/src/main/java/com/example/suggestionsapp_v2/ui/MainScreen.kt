package com.example.suggestionsapp_v2.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.surfaceColorAtElevation
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.focusModifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.suggestionsapp_v2.data.source.FormData
import com.example.suggestionsapp_v2.ui.components.AnimatedCircle
import com.example.suggestionsapp_v2.ui.components.BaseRow

const val TAG = "easytosearch"

@Composable
fun MainScreen(
    mainScreenViewModel: MainScreenViewModel = viewModel(factory = MainScreenViewModel.Factory),
    modifier: Modifier = Modifier,
) {
    val formDataState by mainScreenViewModel.formState.collectAsState()

    Scaffold(
        topBar = {},
        bottomBar = {},
        floatingActionButton = {},
        modifier = modifier,
//        containerColor = MaterialTheme.colorScheme.surface,
        containerColor = MaterialTheme.colorScheme.surface,
        contentColor = MaterialTheme.colorScheme.primaryContainer
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = DEFAULT_PADDING)
                .padding(top = DEFAULT_PADDING, bottom = innerPadding.calculateBottomPadding()),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            val colorList: List<Color> = formDataState.map { formData ->
                Color(formData.color ?: 0)
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
                    totalVotes = formDataState.sumOf { total -> total.votes },
                )
            }
            LazyColumn(
                modifier = modifier
                    .weight(0.4f)
                    .fillMaxWidth()
                    .padding(DEFAULT_PADDING),
                horizontalAlignment = Alignment.Start,
                verticalArrangement = Arrangement.Top
            ) {
                items(formDataState) { message ->
                    BaseRow(
                        color = Color(message.color ?: Color.Red.toArgb()),
                        title = message.optionName.toString().lowercase(),
                        votes = message.votes,
                    )

                }
            }
        }
    }
}

@Composable
fun DisplayCircle(
//fun DisplayCircle(
    items: List<FormData>,
    colors: List<Color>,
    votes: (FormData) -> Int,
    totalVotes: Int,
    modifier: Modifier = Modifier,
) {
    Box(modifier = modifier) {
        AnimatedCircle(
            proportions = items.extractProportions { votes(it) },
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
                text = "Votes",
                style = MaterialTheme.typography.titleLarge,
                modifier = modifier,
//                fontSize = 60.sp,
                color = MaterialTheme.colorScheme.onPrimaryContainer
            )
        }
    }
}

@Composable
fun DisplayRow(
    color: Color,
    optionName: String = "",
    votes: Int = 0,
    modifier: Modifier = Modifier.fillMaxWidth()
) {
    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.Start,

        ) {
        Text(text = votes.toString(), style = TextStyle(fontSize = 24.sp))
        Spacer(modifier = Modifier.padding(horizontal = 16.dp))
        Text(text = optionName, style = TextStyle(fontSize = 24.sp))
    }
}


fun <E> List<E>.extractProportions(selector: (E) -> Int): List<Float> {
    val total = this.sumOf { selector(it).toDouble() }
    return this.map { (selector(it) / total).toFloat() }
}

val DEFAULT_PADDING = 16.dp