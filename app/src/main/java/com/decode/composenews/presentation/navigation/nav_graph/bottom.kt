package com.decode.composenews.presentation.navigation.nav_graph

import android.R.id.home
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.decode.composenews.presentation.navigation.Screen
import com.decode.composenews.presentation.screens.feednews.FeedNewsScreen
import com.decode.composenews.presentation.screens.recordednews.RecordedNewsScreen

fun NavGraphBuilder.bottom(navController: NavController) {

    news(navController)
    composable<Screen.FeedNews> {
        FeedNewsScreen()
    }
    composable<Screen.RecordedNews> {
        RecordedNewsScreen(
            navigate = { newsId ->
                navController.navigate(Screen.Home.Article(newsId))
            }
        )
    }
}