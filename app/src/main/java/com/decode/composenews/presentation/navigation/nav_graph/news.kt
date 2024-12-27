package com.decode.composenews.presentation.navigation.nav_graph

import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import androidx.navigation.toRoute
import com.decode.composenews.presentation.navigation.Screen
import com.decode.composenews.presentation.screens.article.ArticleScreen
import com.decode.composenews.presentation.screens.article.ArticleViewModel
import com.decode.composenews.presentation.screens.home.HomeScreen
import com.decode.composenews.presentation.screens.home.NewsViewModel

fun NavGraphBuilder.news(navController: NavController) {
    navigation<Screen.Home>(startDestination = Screen.News) {
        composable<Screen.News> {
            val viewModel = hiltViewModel<NewsViewModel>()
            val uiState = viewModel.uiState.collectAsStateWithLifecycle()
            val uiEffect = viewModel.uiEffect
            HomeScreen(
                uiState = uiState.value,
                uiEffect = uiEffect,
                onEvent = viewModel::onEvent,
                navigate = { newsId ->
                    navController.navigate(Screen.Article(newsId))
                }
            )
        }

        composable<Screen.Article> { backStackEntry ->
            val viewModel = hiltViewModel<ArticleViewModel>()
            val uiState = viewModel.uiState.collectAsStateWithLifecycle()
            val uiEffect = viewModel.uiEffect
            val argument = backStackEntry.toRoute<Screen.Article>()
            ArticleScreen(
                uiState = uiState.value,
                onEvent = viewModel::onEvent,
                uiEffect = uiEffect,
                newsId = argument.newsId,
                navigateUp = { navController.navigateUp() }
            )
        }
    }

}