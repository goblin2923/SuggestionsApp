package com.example.suggestionsapp_v2.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.clipToBounds
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.semantics.clearAndSetSemantics
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.wear.compose.material.ContentAlpha
import androidx.wear.compose.material.LocalContentAlpha


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
            .background(color = MaterialTheme.colorScheme.onPrimary)
        ,
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
    Column(modifier = Modifier
        .height(88.dp)
        .clearAndSetSemantics {
            contentDescription = "Votes by $name"
        }
        .background(color = MaterialTheme.colorScheme.onPrimary),
        verticalArrangement = Arrangement.Top) {
        Row(
            modifier = modifier,
            verticalAlignment = Alignment.CenterVertically,
        ) {
            val typography = MaterialTheme.typography.bodyMedium
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

        Text(
            text = "Suggestions:",
            modifier = Modifier.padding(start = 24.dp),
            color = MaterialTheme.colorScheme.onPrimaryContainer
        )
        LazyColumn(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 32.dp)
        ) {
            item {
                Text(text = suggestions, textAlign = androidx.compose.ui.text.style.TextAlign.Justify)
            }

        }
    }

    FormOptionDivider()
}

@Composable
fun FormOptionDivider(modifier: Modifier = Modifier) {
    HorizontalDivider(
        modifier = modifier, thickness = 2.dp, color = MaterialTheme.colorScheme.background
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