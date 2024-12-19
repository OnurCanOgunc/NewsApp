package com.decode.composenews.presentation.navigation

import kotlinx.serialization.Serializable

sealed class Screen {
    @Serializable
    object Home : Screen()
    @Serializable
    data class Detail(val newsId: String) : Screen()
}