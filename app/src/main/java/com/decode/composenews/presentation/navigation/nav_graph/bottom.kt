package com.decode.composenews.presentation.navigation.nav_graph

import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.decode.composenews.presentation.navigation.Screen
import com.decode.composenews.presentation.screens.feednews.FeedNewsScreen
import com.decode.composenews.presentation.screens.recordednews.RecordNewsViewModel
import com.decode.composenews.presentation.screens.recordednews.RecordedNewsScreen

fun NavGraphBuilder.bottom(navController: NavController) {

    news(navController)
    composable<Screen.FeedNews> {
        FeedNewsScreen()
    }
    composable<Screen.RecordedNews> {
        val viewModel = hiltViewModel<RecordNewsViewModel>()
        val uiState = viewModel.uiState.collectAsStateWithLifecycle()
        RecordedNewsScreen(
            uiState = uiState.value,
            onEvent = viewModel::onEvent,
            navigate = { newsId ->
                navController.navigate(Screen.Article(newsId))
            }
        )
    }
}