package com.decode.composenews.presentation.navigation

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.toRoute
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import com.decode.composenews.domain.model.News
import com.decode.composenews.presentation.components.BottomAppBar
import com.decode.composenews.presentation.screens.NewsViewModel
import com.decode.composenews.presentation.screens.detail.DetailScreen
import com.decode.composenews.presentation.screens.home.HomeScreen

@Composable
fun NavGraph(
    navController: NavHostController,
    startDestination: Screen = Screen.Home,
) {
    Scaffold(
        bottomBar = { BottomAppBar() }
    ) { innerPadding ->
        NavHost(
            modifier = Modifier.padding(innerPadding),
            navController = navController,
            startDestination = startDestination
        ) {
            composable<Screen.Home> {
                HomeScreen(
                    navigate = { newsId ->
                        navController.navigate(Screen.Detail(newsId))
                    }
                )
            }
            composable<Screen.Detail> { backStackEntry ->
                val argument = backStackEntry.toRoute<Screen.Detail>()
                DetailScreen(
                    newsId = argument.newsId,
                    navigateUp = { navController.navigateUp() }
                )
            }

        }
    }

}