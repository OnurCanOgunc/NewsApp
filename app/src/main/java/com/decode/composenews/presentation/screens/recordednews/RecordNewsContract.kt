package com.decode.composenews.presentation.screens.recordednews

import com.decode.composenews.domain.model.News

object RecordNewsContract {
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
        data class DeleteArticle(val news: News) : RecordNewsUIEvent()
        object RestoreArticle : RecordNewsUIEvent()
    }
    sealed class RecordNewsUIEffect {
        data class SaveMessage(val message: String) : RecordNewsUIEffect()
    }
}