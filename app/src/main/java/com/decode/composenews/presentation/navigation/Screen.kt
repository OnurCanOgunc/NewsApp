package com.decode.composenews.presentation.navigation

import kotlinx.serialization.Serializable

sealed class Screen {
    @Serializable
    object Home {
        @Serializable
        object News : Screen()
        @Serializable
        data class Article(val newsId: String) : Screen()
    }
    @Serializable
    object FeedNews : Screen()
    @Serializable
    object RecordedNews : Screen()
}