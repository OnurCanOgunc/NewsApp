package com.decode.composenews.presentation.navigation

import kotlinx.serialization.Serializable

sealed interface Screen {
    @Serializable
    object News: Screen

    @Serializable
    data class Article(val newsId: String): Screen

    @Serializable
    object RecordedNews : Screen

    @Serializable
    object Home : Screen

}