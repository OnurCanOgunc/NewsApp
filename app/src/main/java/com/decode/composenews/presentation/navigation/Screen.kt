package com.decode.composenews.presentation.navigation

import kotlinx.serialization.Serializable

sealed class Screen {
    @Serializable
    object Home : Screen()
    @Serializable
    object Detail: Screen()
}