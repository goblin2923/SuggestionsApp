package com.example.suggestionsapp_v2

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.suggestionsapp_v2.ui.MainScreen
import com.example.suggestionsapp_v2.ui.theme.SuggestionsAppTheme

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {


        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            SuggestionsAppTheme {
                MainScreen()
            }
        }

    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
        Greeting("Android")

}