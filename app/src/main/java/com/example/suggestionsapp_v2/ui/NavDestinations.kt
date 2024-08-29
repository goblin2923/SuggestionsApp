package com.example.suggestionsapp_v2.ui

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Menu
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.navigation.NavType
import androidx.navigation.navArgument

interface NavDestinations {
    val route: String
    val icon: ImageVector
}

object HomePage: NavDestinations{
    override val route = "Home"
    override val icon = Icons.Filled.Home
}

object SuggestionsPage: NavDestinations{
    override val route = "Suggestions"
    override val icon = Icons.Filled.Menu
}

object FormOptionPage : NavDestinations {
    override val route = "form_option"
    const val optionArg = "option_name"
    val routeWithArgs = "$route/{$optionArg}"
    val arguments = listOf(navArgument(optionArg) { type = NavType.StringType })
    override val icon: ImageVector = Icons.Filled.Menu
}



//object AssistantPage: NavDestinations{
//    override val route = "AssistantPage"
//    override val icon = R.drawable.
//}

val NavTabScreens = listOf(HomePage,SuggestionsPage)


