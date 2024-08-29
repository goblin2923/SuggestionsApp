package com.example.suggestionsapp_v2

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.pullrefresh.PullRefreshIndicator
import androidx.compose.material.pullrefresh.pullRefresh
import androidx.compose.material.pullrefresh.rememberPullRefreshState
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.compose.SuggestionsAppTheme
import com.example.suggestionsapp_v2.data.source.FormData
import com.example.suggestionsapp_v2.ui.FormOptionPage
import com.example.suggestionsapp_v2.ui.HomePage
import com.example.suggestionsapp_v2.ui.NavTabScreens
import com.example.suggestionsapp_v2.ui.SuggestionsPage
import com.example.suggestionsapp_v2.ui.components.SuggestionsNavTab
import com.example.suggestionsapp_v2.ui.screens.DisplayMainScreen
import com.example.suggestionsapp_v2.ui.screens.FormOptionScreen
import com.example.suggestionsapp_v2.ui.screens.MainScreenUiState
import com.example.suggestionsapp_v2.ui.screens.SuggestionsViewModel
import com.example.suggestionsapp_v2.ui.screens.TAG

class SuggestionsActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MainScreen()
        }

    }
}


@OptIn(ExperimentalMaterialApi::class)
@Composable
fun MainScreen(
    suggestionsViewModel: SuggestionsViewModel = viewModel(factory = SuggestionsViewModel.Factory),
    modifier: Modifier = Modifier,
) {
    SuggestionsAppTheme(dynamicColor = false) {

        val uiState by suggestionsViewModel.uiState.collectAsState()
        val pullRefreshState = rememberPullRefreshState(
            refreshing = uiState.isLoading,
            onRefresh = { suggestionsViewModel.refreshForms() },
            )

        var navController = rememberNavController()
        val currentBackStack by navController.currentBackStackEntryAsState()
        val currentDestination = currentBackStack?.destination

        val currentScreen = NavTabScreens.find { it.route == currentDestination?.route } ?: HomePage


        Scaffold(
            topBar = {},
            bottomBar = {
                SuggestionsNavTab(
                    pages = NavTabScreens,
                    onTabSelected = { newScreen ->
                    navController.navigateSingleTopTo(newScreen.route)
                }, currentPage = currentScreen)
            },
            floatingActionButton = {},
            modifier = modifier,
            containerColor = MaterialTheme.colorScheme.surface,
            contentColor = MaterialTheme.colorScheme.primaryContainer
        ) { innerPadding ->
            NavHost(
                navController = navController,
                startDestination = HomePage.route,
                modifier = Modifier.padding(innerPadding)
            ) {
                composable(route = HomePage.route) {
                    Box(
                        modifier = Modifier
                            .padding(innerPadding)
                            .pullRefresh(pullRefreshState)
                    ) {
                        DisplayMainScreen(
                            uiState = uiState,
                            onAccountClick = {
                                optionName ->
                                navController.navigateSingleTopTo("${FormOptionPage.route}/$optionName")
                            }
                        )

                        PullRefreshIndicator(
                            refreshing = uiState.isLoading,
                            state = pullRefreshState,
                            modifier = Modifier.align(Alignment.TopCenter),
                            backgroundColor = MaterialTheme.colorScheme.onPrimaryContainer,
                        )
                    }
                }
                composable(route = SuggestionsPage.route) {
                    Log.w("search", "second screen recomposing: ", )
                    DisplayFormNames(uiState = uiState)
                }
                composable(
                    route = FormOptionPage.routeWithArgs,
                    arguments = FormOptionPage.arguments
                ) { navBackStackEntry ->
                    val optionName = navBackStackEntry.arguments?.getString(FormOptionPage.optionArg)
                    if (optionName != null) {
                        val option = FormData.Options.valueOf(optionName)
                        FormOptionScreen(option)
                    }
                }
            }
        }
    }
}

@Composable
fun DisplayFormNames(uiState: MainScreenUiState) {
    Column {
        uiState.forms.forEach { formData ->
            Text(
                text = "Option: ${formData.formData.optionName}, "
            )
        }
    }
}


fun NavHostController.navigateSingleTopTo(route: String) =
    this.navigate(route) {
        // Pop up to the start destination of the graph to
        // avoid building up a large stack of destinations
        // on the back stack as users select items
        popUpTo(this@navigateSingleTopTo.graph.findStartDestination().id) {
            saveState = true
        }
        // Avoid multiple copies of the same destination when
        // reselecting the same item
        launchSingleTop = true
        // Restore state when reselecting a previously selected item
        restoreState = true
    }