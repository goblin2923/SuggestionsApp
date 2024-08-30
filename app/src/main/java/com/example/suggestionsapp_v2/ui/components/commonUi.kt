package com.example.suggestionsapp_v2.ui.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowLeft
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.semantics.clearAndSetSemantics
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale


@Composable
fun BaseRow(
    modifier: Modifier = Modifier,
    color: Color,
    title: String,
    votes: Int,
) {
    Row(
        modifier = modifier
            .height(68.dp)
            .clearAndSetSemantics {
                contentDescription = "Votes for $title: $votes"
            }
            .background(color = MaterialTheme.colorScheme.onPrimary),
        verticalAlignment = Alignment.CenterVertically,

        ) {
        val typography = MaterialTheme.typography
        ColorIndicator(
            color = color, modifier = Modifier
        )
        Spacer(Modifier.width(12.dp))
        Text(
            text = title,
            style = typography.headlineLarge,
            color = MaterialTheme.colorScheme.onPrimaryContainer,
            modifier = Modifier.padding(horizontal = 8.dp)
        )
        Spacer(Modifier.weight(1f))
        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
        ) {
            Text(
                text = votes.toString(),
                style = typography.headlineLarge,
                modifier = Modifier
                    .align(Alignment.CenterVertically)
                    .padding(horizontal = 8.dp),
                color = MaterialTheme.colorScheme.onPrimaryContainer
            )
        }
        Spacer(Modifier.width(16.dp))

//        CompositionLocalProvider(LocalContentAlpha provides ContentAlpha.medium) {
//            Icon(
//                imageVector = Icons.Filled.FavoriteBorder,
//                contentDescription = null,
//                modifier = Modifier
//                    .padding(end = 12.dp)
//                    .size(24.dp),
//                tint = MaterialTheme.colorScheme.surface
//            )
//        }
    }
    FormOptionDivider()
}

@Composable
fun BaseRow(
    modifier: Modifier = Modifier,
    color: Color,
    name: String,
    suggestions: String,
    time: String,
) {
    var expanded by remember { mutableStateOf(false) }

    Column(modifier = Modifier
        .height(IntrinsicSize.Min)
        .clearAndSetSemantics {
            contentDescription = "Votes by $name"
        }
        .background(color = MaterialTheme.colorScheme.onPrimary)
        .clickable { expanded = !expanded }, verticalArrangement = Arrangement.Top
    ) {
        Row(
            modifier = modifier,
            verticalAlignment = Alignment.CenterVertically,
        ) {
            val typography = MaterialTheme.typography.bodyLarge
            val textColor = MaterialTheme.colorScheme.onPrimaryContainer
            ColorIndicator(
                color = color, modifier = Modifier
            )
            Spacer(Modifier.width(12.dp))

            Row(
                horizontalArrangement = Arrangement.SpaceBetween, modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    text = name,
                    style = typography,
                    color = textColor,
                    modifier = Modifier.padding(horizontal = 8.dp)
                )
                Text(
                    text = time,
                    style = typography,
                    modifier = Modifier.padding(horizontal = 8.dp),
                    color = textColor
                )
            }
        }

        AnimatedVisibility(
            visible = expanded, exit = fadeOut(), enter = fadeIn()
        ) {

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 32.dp)
            ) {
                Text(
                    text = "Suggestions:", color = MaterialTheme.colorScheme.primary
                )
                Text(
                    text = suggestions,
                    textAlign = TextAlign.Justify,
                    color = MaterialTheme.colorScheme.primary,
                    modifier = Modifier
                        .padding(bottom = 8.dp)
                        .padding(start = 4.dp)
                )
            }
        }
    }
    FormOptionDivider()
}

@Composable
fun BaseRow(
    modifier: Modifier = Modifier,
    color: Color,
    id: String,
    title: String,
    content: String,
    time: Long,
    onDelete: (String) -> Unit
) {
    var expanded by remember { mutableStateOf(false) }

    Column(modifier = Modifier
        .height(IntrinsicSize.Min)
        .clearAndSetSemantics {
            contentDescription = "title $title"
        }
        .background(color = MaterialTheme.colorScheme.onPrimary)
        .clickable { expanded = !expanded }, verticalArrangement = Arrangement.Top
    ) {
        val typography = MaterialTheme.typography.bodyLarge
        val textColor = MaterialTheme.colorScheme.onPrimaryContainer
        Row(
            modifier = modifier,
            verticalAlignment = Alignment.CenterVertically,
        ) {
            ColorIndicator(
                color = color, modifier = Modifier
            )
            Spacer(Modifier.width(12.dp))

            Box(
//                contentAlignment = Alignment.,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    text = title,
                    style = typography,
                    color = textColor,
                    modifier = Modifier
                        .padding(horizontal = 8.dp)
                        .align(Alignment.TopStart)
                )

                Icon(Icons.Default.Close,
                    contentDescription = null,
                    modifier = Modifier
                        .clickable { onDelete(id) }
                        .padding(horizontal = 8.dp)
                        .align(Alignment.BottomEnd),
                    tint = textColor)
            }
        }

        AnimatedVisibility(visible = expanded, exit = fadeOut(), enter = fadeIn()) {

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 32.dp)
            ) {
                Row(
                    horizontalArrangement = Arrangement.SpaceBetween,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(
                        text = "Time:", color = MaterialTheme.colorScheme.primary
                    )
                    Text(
                        text = formatTimestamp(time),
                        style = typography,
                        modifier = Modifier.padding(horizontal = 8.dp),
                        color = textColor
                    )
                }
                Text(
                    text = "Content:",
                    color = MaterialTheme.colorScheme.primary,
                    modifier = Modifier.padding(horizontal = 4.dp)
                )
                Text(
                    text = content,
                    textAlign = TextAlign.Justify,
                    color = MaterialTheme.colorScheme.primary,
                    modifier = Modifier
                        .padding(bottom = 8.dp)
                        .padding(start = 4.dp)
                )
            }
        }
    }
    FormOptionDivider()
}

@Composable
fun GoBack(
           goBack: () -> Unit){
    Box(Modifier.padding(start = 12.dp, top = 24.dp)
        .clickable { goBack }
    ){
        Icon(Icons.AutoMirrored.Filled.KeyboardArrowLeft,
            contentDescription = null,
            Modifier.clickable { goBack }
                .size(32.dp),
            tint = MaterialTheme.colorScheme.onPrimaryContainer,

        )
    }
}

@Composable
fun FormOptionDivider(
    modifier: Modifier = Modifier, color: Color = MaterialTheme.colorScheme.background
) {
    HorizontalDivider(
        modifier = modifier, thickness = 2.dp, color = color
    )
}

/**
 * A vertical colored line.
 */
@Composable
private fun ColorIndicator(color: Color, modifier: Modifier = Modifier) {
    Spacer(
        modifier
            .size(8.dp, 36.dp)
            .background(color = color)
    )
//    Spacer(
//        modifier
////            .padding(start = 4.dp)
//            .size(1.dp, 36.dp)
//            .background(color = Color.White)
//            .alpha(0.1f)
//    )
}


@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    BaseRow(
        color = Color.Red,
        name = "hammad",
        time = "12:00",
        suggestions = "adjksja nsakjlsansakjkf aslfjslaf akfjksa ksjaf ksajf sakfnkjfa saknj asjkfksfa "
    )
}

fun formatTimestamp(timestamp: Long): String {
    val date = Date(timestamp)
    val format = SimpleDateFormat("dd MMM yyyy, hh:mm a", Locale.getDefault())
    return format.format(date)
}