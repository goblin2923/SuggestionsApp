package com.example.suggestionsapp_v2.ui.components

import androidx.compose.animation.Animatable
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.animateTo
import androidx.compose.animation.core.tween
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.selection.selectableGroup
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.semantics.clearAndSetSemantics
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.suggestionsapp_v2.ui.NavDestinations
import kotlinx.coroutines.launch

@Composable
fun SuggestionsNavTab(
    pages: List<NavDestinations>,
    onTabSelected: (NavDestinations) -> Unit,
    currentPage: NavDestinations
) {
    val color = MaterialTheme.colorScheme.onSurface
    val tabTintColors = remember {
        pages.map { Animatable(if (it == currentPage) color else color.copy(alpha = InactiveTabOpacity)) }
    } // Now holds Animatable objects

    val animSpec = remember {
        tween<Color>(
            durationMillis = TabFadeInAnimationDuration, // Use a consistent duration
            easing = LinearEasing,
            delayMillis = TabFadeInAnimationDelay)
    }

    LaunchedEffect(currentPage) {
        tabTintColors.forEachIndexed { index, animatable ->
            val targetValue = if (pages[index] == currentPage) color else color.copy(alpha = InactiveTabOpacity)
            launch {
                animatable.animateTo(
                    targetValue = targetValue,
                    animationSpec = animSpec
                )
            }
        }
    }

    Surface(
        Modifier
            .height(TabHeight)
            .fillMaxWidth(),
        color = MaterialTheme.colorScheme.primaryContainer,
    ) {
        Row(
            Modifier.selectableGroup(), horizontalArrangement = Arrangement.Center
        ) {
            pages.forEachIndexed { index, screen ->
                NavTab(
                    text = screen.route,
                    icon = screen.icon,
                    onSelected = { onTabSelected(screen) },
                    selected = currentPage == screen,
                    tabTintColor = tabTintColors[index].value
                )
            }

        }
    }
}

@Composable
private fun NavTab(
    text: String,
    icon: ImageVector,
    onSelected: () -> Unit,
    selected: Boolean,
    tabTintColor: Color
) {

//    val color = androidx.compose.material.MaterialTheme.colors.onSurface
//    val animSpec = remember {
//        tween<Color>(
//            durationMillis = durationMillis,
//            easing = LinearEasing,
//            delayMillis = TabFadeInAnimationDelay
//        )
//    }
//    val tabTintColor by animateColorAsState(
//        targetValue = if (selected) color else color.copy(alpha = InactiveTabOpacity),
//        animationSpec = animSpec
//    )
    Row(
        modifier = Modifier
            .padding(16.dp)
            .animateContentSize()
            .height(TabHeight)
            .selectable(
                selected = selected,
                onClick = onSelected,
                role = Role.Tab,
                interactionSource = remember { MutableInteractionSource() },
                indication = rememberRipple(
                    bounded = false,
                    radius = Dp.Unspecified,
                    color = Color.Unspecified
                )
            )
            .clearAndSetSemantics { contentDescription = text }
    ) {
        Icon(imageVector = icon, contentDescription = text, tint = tabTintColor)
        if (selected) {
            Spacer(Modifier.padding(horizontal = 8.dp))
            Text(
                text,
                color = MaterialTheme.colorScheme.onSurface,
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,

                )

        }
    }
}

private val TabHeight = 56.dp
private const val InactiveTabOpacity = 0.50f

private const val TabFadeInAnimationDuration = 150
private const val TabFadeInAnimationDelay = 100
private const val TabFadeOutAnimationDuration = 100

//@Preview(showBackground = true)
//@Composable
//fun disp(){
//    NavTab(text = "help", icon = painterResource(id = R.drawable.bulab), onSelected = { /*TODO*/ }, selected =true )
//}

