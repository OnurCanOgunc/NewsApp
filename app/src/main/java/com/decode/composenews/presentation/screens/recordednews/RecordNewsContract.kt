package com.decode.composenews.presentation.screens.recordednews

import com.decode.composenews.domain.model.News

sealed class RecordNewsContract {
    //@Immutable
    data class RecordNewsUIState(
        val news: List<News> = emptyList(),
        val isLoading: Boolean = false,
        val isError: Boolean = false,
        val errorMessage: String = ""
    )

    sealed class RecordNewsUIEvent {
        object LoadSavedArticles : RecordNewsUIEvent()
        data class SaveArticle(val news: News) : RecordNewsUIEvent()
    }
}