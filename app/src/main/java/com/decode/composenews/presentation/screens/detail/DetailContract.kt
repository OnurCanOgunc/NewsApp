package com.decode.composenews.presentation.screens.detail

import com.decode.composenews.domain.model.News

object DetailContract {
    data class DetailUIState(
        val news: News? = null,
        val isLoading: Boolean = false,
        val isError: Boolean = false
    )
    sealed class DetailUIEvent {
        data class Load(val id: String) : DetailUIEvent()
        data object Share: DetailUIEvent()
        data class Save(val news: News?) : DetailUIEvent()
    }
    sealed class DetailUIEffect {
        object NavigateToDetail : DetailUIEffect()
    }
}