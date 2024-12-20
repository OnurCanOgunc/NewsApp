package com.decode.composenews.presentation.navigation

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.toRoute
import com.decode.composenews.presentation.components.BottomAppBar
import com.decode.composenews.presentation.screens.article.ArticleScreen
import com.decode.composenews.presentation.screens.feednews.FeedNewsScreen
import com.decode.composenews.presentation.screens.home.HomeScreen
import com.decode.composenews.presentation.screens.recordednews.RecordedNewsScreen

@Composable
fun NavGraph(
    navController: NavHostController,
    startDestination: Screen = Screen.Home,
) {
    Scaffold(
        bottomBar = { BottomAppBar(navController = navController) }
    ) { innerPadding ->
        NavHost(
            modifier = Modifier.padding(innerPadding),
            navController = navController,
            startDestination = startDestination
        ) {
            composable<Screen.Home> {
                HomeScreen(
                    navigate = { newsId ->
                        navController.navigate(Screen.Article(newsId))
                    }
                )
            }
            composable<Screen.Article> { backStackEntry ->
                val argument = backStackEntry.toRoute<Screen.Article>()
                ArticleScreen(
                    newsId = argument.newsId,
                    navigateUp = { navController.navigateUp() }
                )
            }
            composable<Screen.FeedNews> {
                FeedNewsScreen()
            }
            composable<Screen.RecordedNews> {
                RecordedNewsScreen(
                    navigate = { newsId ->
                        navController.navigate(Screen.Article(newsId))
                    }
                )
            }

        }
    }

}