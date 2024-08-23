package com.example.suggestionsapp_v2.ui

import android.view.Display
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.suggestionsapp_v2.ui.components.AnimatedCircle
import com.example.suggestionsapp_v2.ui.theme.SuggestionsApp_V2Theme

@Composable
fun MainScreen(
    mainScreenViewModel: MainScreenViewModel = viewModel(), modifier: Modifier = Modifier
) {
    Scaffold(topBar = {}, bottomBar = {}, floatingActionButton = {}, modifier = modifier
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = DEFAULT_PADDING)
                .padding(top = DEFAULT_PADDING, bottom = innerPadding.calculateBottomPadding()),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Column(
                modifier = modifier
                    .weight(.4f)
                    .fillMaxWidth()
                    .padding(DEFAULT_PADDING),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center,

                ) {
                DisplayCircle()
            }
            Column(
                modifier = modifier
                    .weight(0.6f)
                    .fillMaxWidth()
                    .padding(DEFAULT_PADDING),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(text = "hello world!")
            }
        }
    }
}

@Composable
//fun <T> DisplayCircle(
fun DisplayCircle(
//    items: List<T>,
//    colors: (T) -> List<Color>,
//    votes: (T) -> List<Int>,
    modifier: Modifier = Modifier,
) {
    Box(modifier = modifier) {
        val size = IntSize.Zero
        AnimatedCircle(
            proportions = listOf(0.3f, 0.4f, 0.3f),
            colors = listOf(Color.Red, Color.Cyan, Color.Magenta),
            Modifier
                .padding(DEFAULT_PADDING)
                .height(300.dp)
                .fillMaxSize()
        )
        Column(modifier = Modifier.align(alignment = Alignment.Center)) {
            Text(
                text = "100",
                modifier = Modifier.align(Alignment.CenterHorizontally)
            )
        }
    }
}


@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    SuggestionsApp_V2Theme {
        MainScreen()
    }
}

val DEFAULT_PADDING = 16.dp